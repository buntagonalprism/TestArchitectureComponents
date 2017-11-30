package com.buntagon.testarchitecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toolbar
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.ui.addbook.AddEditBookFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsFragment
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsViewModel
import com.buntagon.testarchitecturecomponents.ui.library.LibraryFragment
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultFragmentIfNoneLoaded()

        setupActivityObservers()
        setupBookObservers()
        setupAuthorObservers()

        initialiseDrawerNavigation()

    }

    private fun setDefaultFragmentIfNoneLoaded() {
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.contentFrame)
        if (currentFragment == null) {
            loadFragment(LibraryFragment())
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

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

        initialiseDrawerNavigation()
    }

    /**
     * What a roller coaster getting this working was. The thing it turned out to understand was
     * to stop looking at ActionBar methods, given toolbar is a replacement for it anyway. The
     * focus instead needed to be on the toolbar
     *
     * The setHomeAsUp stuff is really more about the old action bar way of doing things - the pure
     * toolbar approach uses instead what is called a navigation icon, a generic configurable icon
     * to display in the top left of the toolbar. We can listen to clicks on this navigation icon
     * using the myToolbar.setNavigationClickListener method.
     *
     * Applying a navigation icon to a toolbar (which will automatically cause it it display when
     * the icon is non-null) can be done either through the programmatic myToolbar.setNavigationIcon
     * method or in XML using the 'navigationIcon' attribute and 'navigationContentDescription'
     * Note that automatic display when non-null also means no default image display when we don't
     * supply one ourselves. The different methods for setting home as up enabled can force a
     * toolbar to show an default up icon, but its not really the intended pattern
     *
     * So, that's for the basic toolbar so far, but its a slightly different ball game when the
     * drawer toggle gets involved. We still don't use any homeAsUp() functions, but it turns out
     * the drawer toggle is fairly sophisticated, able to switch between showing a standard
     * hamburger toggle icon for the navigation drawer, and displaying the toolbar navigation icon.
     *
     * In brief:
     * * if isDrawerIndicatorEnabled == true, then the toolbar will include the hamburger which will
     *     toggle the navigation icon
     * * if isDrawerIndicatorEnabled == false, then the toolbar will display the navigation icon if
     *     one is specified in the theme
     * * if a navigation icon is showing, any clicks on it will be passed to the listener
     *     myDrawerToggle.setToolbarNavigationClickListener
     * * We shouldn't use the myToolbar.setNavigationClickListener method because the drawer toggle
     *     uses this method to listen for clicks to open/close the drawer, so we use the above
     *     method instead to let the toggle decide whether a click is a toggle event or a navigation
     *     event
     */
    private fun initialiseDrawerNavigation() {
        // Set support toolbar as activity support action bar
        setSupportActionBar(toolbar)

        // Create a drawer toggle within our toolbar, used for opening the navigation drawer
        val drawerToggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.action_open, R.string.action_close )


        // Inform the navigation drawer about the toggle. This allows the drawer to notify the
        // toggle for example when the user swipes the drawer closed
        drawer_layout.addDrawerListener(drawerToggle)

        // What to do when the standard toolbar navigation icon is shown instead of the drawer
        // toggle icon (i.e. when isDrawerIndicatorEnabled == false)
        // Here we are using it as a simulated back button
        drawerToggle.setToolbarNavigationClickListener {
            onBackPressed()
        }

        // Used to make sure the toggle loads correct drawer in / out state after activity recreated
        drawerToggle.syncState()

        // Listen for presses on the navigation drawer
        navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_books -> loadOuterFragment(LibraryFragment())
                R.id.nav_authors -> loadOuterFragment(AuthorsFragment())
            }
            drawer_layout.closeDrawer(navigation)
            true
        }
        mDrawerToggle = drawerToggle
    }

    override fun onBackPressed() {
        // Super call will pop fragment back-stack
        super.onBackPressed()

        // Release drawer
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        // Restore hamburger menu icon
        mDrawerToggle?.isDrawerIndicatorEnabled = true
    }




    private fun loadInnerFragment(newFragment: Fragment) {
        loadFragment(newFragment)
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mDrawerToggle?.isDrawerIndicatorEnabled = false
    }

    private fun loadOuterFragment(newFragment: Fragment) {
        loadFragment(newFragment)
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mDrawerToggle?.isDrawerIndicatorEnabled = true
    }


    private fun loadFragment(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.contentFrame, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }


}
