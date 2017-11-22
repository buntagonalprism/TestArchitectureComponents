package com.buntagon.testarchitecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.ui.addbook.AddEditBookFragment
import com.buntagon.testarchitecturecomponents.ui.library.LibraryFragment
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "My Books"
        // Create new fragment and transaction
        createMasterScreen()


        val viewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)

        // Add new book
        fab_add_task.setOnClickListener {
            title = "Create Book"
            goToDetailScreen()
        }

        // Edit book
        viewModel.editEvent.observe(this, Observer {
            title = "Edit Book"
            goToDetailScreen()
        })

        // Back navigation after adding / editing book
        viewModel.saveCompleteEvent.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onBackPressed() {
        // Will pop fragment backstack
        fab_add_task.visibility = View.VISIBLE
        title = "My Books"
        super.onBackPressed()
    }

    private fun createMasterScreen() {
        fab_add_task.visibility = View.VISIBLE
        val newFragment = LibraryFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.contentFrame, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }

    private fun goToDetailScreen() {
        fab_add_task.visibility = View.GONE
        val detailFragment = AddEditBookFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, detailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



}
