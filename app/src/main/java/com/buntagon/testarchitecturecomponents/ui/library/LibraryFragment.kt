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
import com.buntagon.testarchitecturecomponents.util.replaceObserver
import kotlinx.android.synthetic.main.fragment_books.*


/**
 * A fragment representing a list of books.
 */

class LibraryFragment : Fragment() {
    
    var mAdapter: BookAdapter? = null
    var mViewModel: LibraryViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the view model
        mViewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        // Set the item click listener
        val selectListener : (Book) -> Unit = { book ->
            mViewModel?.editBook(book.id)
        }

        val deleteListener: (Book) -> Unit = { book ->
            mViewModel?.delete(book)
        }

        // Set the adapter
        mAdapter = BookAdapter(selectListener, deleteListener)

        // Observe data changes and update adapter as needed
        mViewModel?.books?.observe(this, Observer { newBooks ->
            newBooks.let {
                mAdapter?.setData(newBooks!!)
                mViewModel?.books?.applyChangesToAdapter(mAdapter)
            }
        })
        
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_books, container, false)
    }

    /**
     * Under a live data model, onViewCreated does not have many responsibilities - mostly binding
     * adapters and click listeners. Populating adapters with data, or loading values into fields,
     * should be done with observer callbacks
     */
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_list.layoutManager= LinearLayoutManager(view?.context)
        rv_list.adapter = mAdapter


        // Add books
        fab_add_book.setOnClickListener {
            mViewModel?.createBook()
        }

        setTitle()
    }

    private fun setTitle() {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(R.string.title_books_list)
    }

}
