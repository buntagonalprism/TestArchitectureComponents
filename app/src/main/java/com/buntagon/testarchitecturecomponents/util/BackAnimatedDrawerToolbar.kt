package com.buntagon.testarchitecturecomponents.util

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import com.buntagon.testarchitecturecomponents.R


/**
 * Provides a toolbar with a navigation drawer toggle which can alternate between a hamburger icon
 * and back a back arrow. Transition between states is animated. Ideal for an activity swapping a
 * master fragment with has a side menu, for a detail fragment with a back arrow back to master
 *
 * Created by Alex on 30/11/2017.
 */
class BackAnimatedDrawerToolbar(context: Context, attributeSet: AttributeSet) : Toolbar(context, attributeSet){

    private val mArrowDrawable = DrawerArrowDrawable(context)
    private var mShowingToggle = true
    private var mDrawerLayout : DrawerLayout? = null


    /**
     * Initialise the toolbar with a navigation drawer to control and a listener event when acting
     * as a back button
     *
     * @param drawerLayout Navigation drawer layout which should be shown or hidden with the toggle
     * @param navigationListener Optional listener for handling toolbar navigation icon clicks when the
     * drawer toggle is not being shown
     * @param toggleColour Optional colour for the toggle, defaults to the toolbar titleTextColour if
     * not supplied - this behaviour is recommended for visual consistency
     */
    fun attachDrawer(drawerLayout: DrawerLayout, navigationListener: () -> Unit, toggleColour : Int = -1) {

        // If a colour is supplied, use it, otherwise get from the theme
        if (toggleColour >= 0) {
            mArrowDrawable.color = toggleColour
        } else {
            val ta = context.theme.obtainStyledAttributes(intArrayOf(R.attr.titleTextColor))
            mArrowDrawable.color = ta.getColor(0, ContextCompat.getColor(context, android.R.color.white))
            ta.recycle()
        }

        // Spin for the win
        mArrowDrawable.isSpinEnabled = true

        // Set the toolbar the navigation icon
        navigationIcon = mArrowDrawable

        // Use the supplied navigation listener only if we are not currently showing the toggle
        setNavigationOnClickListener {
            if (mShowingToggle) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            } else {
                navigationListener()
            }
        }
    }

    /**
     * Enable the side drawer and show a hamburger menu
     */
    fun showDrawerToggle() {
        mShowingToggle = true
        ObjectAnimator.ofFloat(mArrowDrawable, "progress", 0.0f).start()
        mDrawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Lock the side drawer and show a back arrow
     */
    fun showBackArrow() {
        mShowingToggle = false
        ObjectAnimator.ofFloat(mArrowDrawable, "progress", 1.0f).start()
        mDrawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }



    private fun restoreState() {
        if (mShowingToggle) {
            showDrawerToggle()
        }
        else {
            showBackArrow()
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putBoolean("is_showing_toggle", mShowingToggle)
        return bundle
    }

    public override fun onRestoreInstanceState(state: Parcelable?) {
        // implicit null check
        var superState = state
        if (state is Bundle) {
            mShowingToggle = state.getBoolean("is_showing_toggle")
            restoreState()
            superState = state.getParcelable("superState")

        }
        super.onRestoreInstanceState(superState)
    }
}
