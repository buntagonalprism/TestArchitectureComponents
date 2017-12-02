package com.buntagon.testarchitecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.ui.addbook.AddEditBookFragment
import com.buntagon.testarchitecturecomponents.ui.authordetails.AddEditAuthorFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsViewModel
import com.buntagon.testarchitecturecomponents.ui.library.LibraryFragment
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val popBackStackToFragmentFlags = 0      // Alternative is to pop beyond the selected fragment

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
            loadFragment(LibraryFragment(), addToBackStack = false)
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

        authorViewModel.createEvent.observe(this, Observer {
            loadInnerFragment(AddEditAuthorFragment())
        })

        authorViewModel.editEvent.observe(this, Observer {
            loadInnerFragment(AddEditAuthorFragment())
        })

        authorViewModel.saveCompleteEvent.observe(this, Observer {
            onBackPressed()
        })
    }


    private fun initNavigationDrawerToggle() {
        setSupportActionBar(toolbar)
        toolbar?.attachDrawer(drawer_layout, {
            onBackPressed()
        })
        toolbar?.showDrawerToggle()
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
        toolbar?.showDrawerToggle()
        hideKeyboard()
    }

    private fun loadInnerFragment(newFragment: Fragment) {
        loadFragment(newFragment)
        toolbar?.showBackArrow()
    }

    private fun loadOuterFragment(newFragment: Fragment) {
        loadFragment(newFragment)
        toolbar?.showDrawerToggle()
    }


    /**
     * Load a new fragment into the activity
     *
     * @param newFragment Fragment to add to the activity
     * @param popExisting whether to pop an existing fragment from the back stack with the same
     * class name as the one being added. Useful for navigating back and forth between master
     * fragments e.g. using a navigation drawer, to prevent a large stack
     * @param addToBackStack Whether to add the fragment to the back stack. Useful for initally
     * added fragment
     */
    private fun loadFragment(newFragment: Fragment, popExisting : Boolean = true, addToBackStack : Boolean = true) {

        // We don't want the keyboard to stay open while moving between fragments
        hideKeyboard()

        // Use the fragment class name as an identifier for finding it in the back stack
        val fragmentName = newFragment.javaClass.name

        // Pop a fragment from the back stack if one is present with a matching name
        var fragToPop = false
        if (popExisting) {
            fragToPop = supportFragmentManager.popBackStackImmediate(fragmentName, popBackStackToFragmentFlags)
        }

        // If we are not popping backstack, or there was no fragment to pop, replace fragment in
        // transaction
        if (!popExisting || !fragToPop) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.contentFrame, newFragment)
            // Add the transaction to the back stack if required
            if (addToBackStack)
                transaction.addToBackStack(fragmentName)

            // Commit the transaction
            transaction.commit()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


}
