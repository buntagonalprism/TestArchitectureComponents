package com.buntagon.testarchitecturecomponents.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet


/**
 * Toolbar making use of the
 *
 * Created by Alex on 30/11/2017.
 */
class BackAnimatedDrawerToolbar(context: Context, attributeSet: AttributeSet) : Toolbar(context, attributeSet){

    private var mDrawerToggle :BackAnimatedDrawerToggle? = null
    private var mShowingToggle = true

    fun attachDrawer(activity: Activity, drawerLayout: DrawerLayout, navigationListener: () -> Unit, toggleColour : Int = -1) {
        mDrawerToggle = BackAnimatedDrawerToggle(activity, this, drawerLayout, navigationListener, toggleColour)
    }

    fun showDrawerToggle() {
        mShowingToggle = true
        mDrawerToggle?.showDrawerToggle()
    }

    fun showBackArrow() {
        mShowingToggle = false
        mDrawerToggle?.showBackArrow()
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
