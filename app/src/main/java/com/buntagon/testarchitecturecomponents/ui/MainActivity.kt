package com.buntagon.testarchitecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.ui.addbook.AddEditBookFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsViewModel
import com.buntagon.testarchitecturecomponents.ui.library.LibraryFragment
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import com.buntagon.testarchitecturecomponents.util.BackAnimatedDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mDrawerToggle: BackAnimatedDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultFragmentIfNoneLoaded()

        setupActivityObservers()
        setupBookObservers()
        setupAuthorObservers()

        initNavigationDrawerToggle()

        initNavigationOptions()
    }

    private fun setDefaultFragmentIfNoneLoaded() {
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.contentFrame)
        if (currentFragment == null) {
            // Don't add the default fragment to the backstack, otherwise the system back button
            // will remove it and leave an empty screen
            loadFragment(LibraryFragment(), false)
        }
    }

    private fun setupActivityObservers() {
        val activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.observe(this, Observer { newTitle ->
            title = newTitle
        })
    }

    private fun setupBookObservers() {
        val bookViewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)
        // Add new book
        bookViewModel.createEvent.observe( this, Observer {
            loadInnerFragment(AddEditBookFragment())
        })

        // Edit book
        bookViewModel.editEvent.observe(this, Observer {
            loadInnerFragment(AddEditBookFragment())
        })

        // Back navigation after adding / editing book
        bookViewModel.saveCompleteEvent.observe(this, Observer {
            onBackPressed()
        })
    }

    private fun setupAuthorObservers() {
        val authorViewModel = ViewModelProviders.of(this).get(AuthorsViewModel::class.java)
    }




    private fun initNavigationDrawerToggle() {
        setSupportActionBar(toolbar)

        mDrawerToggle = BackAnimatedDrawerToggle(this, toolbar, drawer_layout, {
            onBackPressed()
        })
        mDrawerToggle!!.showDrawerToggle()
    }

    private fun initNavigationOptions() {
        navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_books -> loadOuterFragment(LibraryFragment())
                R.id.nav_authors -> loadOuterFragment(AuthorsFragment())
            }
            drawer_layout.closeDrawer(navigation)
            true
        }
    }

    override fun onBackPressed() {
        // Super call will pop fragment back-stack or close app if there is no back-stack
        super.onBackPressed()
        mDrawerToggle?.showDrawerToggle()
    }

    private fun loadInnerFragment(newFragment: Fragment) {
        loadFragment(newFragment, true)
        mDrawerToggle?.showBackArrow()
    }

    private fun loadOuterFragment(newFragment: Fragment) {
        loadFragment(newFragment, true)
        mDrawerToggle?.showDrawerToggle()
    }


    private fun loadFragment(newFragment: Fragment, addToBackStack : Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        transaction.replace(R.id.contentFrame, newFragment)

        // Add the transaction to the back stack if required
        if (addToBackStack)
            transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }


}
