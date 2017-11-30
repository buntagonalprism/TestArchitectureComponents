package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_books.*


/**
 * A fragment representing a list of books.
 */

class LibraryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the view model
        val booksViewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        // Set the item click listener
        val selectListener : (Book) -> Unit = { book ->
            booksViewModel.editBook(book.id)
        }

        val deleteListener: (Book) -> Unit = { book ->
            booksViewModel.delete(book)
        }

        // Set the adapter
        val adapter = BookAdapter(selectListener, deleteListener)
        rv_list.layoutManager= LinearLayoutManager(view?.context)
        rv_list.adapter = adapter


        // Observe data changes and update adapter
        booksViewModel.books.observe(this, Observer { newBooks ->
            newBooks.let {
                adapter.setData(newBooks!!)
            }
        })

        // Add books
        fab_add_book.setOnClickListener {
            booksViewModel.createBook()
        }

        setTitle()

    }

    private fun setTitle() {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(R.string.title_books_list)
    }

}
