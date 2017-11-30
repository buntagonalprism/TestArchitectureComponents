package com.buntagon.testarchitecturecomponents.util

import android.animation.ObjectAnimator
import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import com.buntagon.testarchitecturecomponents.R


/**
 * Provides a toolbar navigator drawer toggle which can alternate between a hamburger icon and back
 * arrow. Transition between states is animated. Ideal for an activity swapping a master fragment
 * with has a side menu, for a detail fragment with a back arrow back to master
 *
 * @param context Activity context this toolbar toggle will be displayed in
 * @param toolbar Toolbar which should include the drawer toggle
 * @param drawerLayout Navigation drawer layout which should be shown or hidden with the toggle
 * @param navigationListener Optional listener for handling toolbar navigation icon clicks when the
 * drawer toggle is not being shown
 * @param toggleColour Optional colour for the toggle, defaults to the toolbar titleTextColour if
 * not supplied - this behaviour is recommended for visual consistency
 *
 * Created by Alex on 30/11/2017.
 */
class BackAnimatedDrawerToggle
internal constructor(context: Activity,
                     toolbar: Toolbar,
                     private val drawerLayout: DrawerLayout,
                     navigationListener: () -> Unit = {},
                     toggleColour: Int = -1) {

    private val mArrowDrawable = DrawerArrowDrawable(context)
    private var mShowingToggle = true

    init {
        // If a colour is supplied, use it, otherwise get from the theme
        if (toggleColour >= 0) {
            mArrowDrawable.color = toggleColour
        } else {
            val ta = context.theme.obtainStyledAttributes(intArrayOf(R.attr.titleTextColor))
            mArrowDrawable.color = ta.getColor(0, ContextCompat.getColor(context, android.R.color.white))
            ta.recycle()
        }
        // Spin For the Win
        mArrowDrawable.isSpinEnabled = true

        toolbar.navigationIcon = mArrowDrawable
        toolbar.setNavigationOnClickListener {
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

    fun showDrawerToggle() {
        ObjectAnimator.ofFloat(mArrowDrawable, "progress", 0.0f).start()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mShowingToggle = true
    }

    fun showBackArrow() {
        ObjectAnimator.ofFloat(mArrowDrawable, "progress", 1.0f).start()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mShowingToggle = false
    }

}
