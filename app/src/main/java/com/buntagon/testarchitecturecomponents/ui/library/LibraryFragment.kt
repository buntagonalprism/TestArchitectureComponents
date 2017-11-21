package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Book
import java.util.*


/**
 * A fragment representing a list of books.
 */

class LibraryFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1

    private var books : MutableList<Book> = Arrays.asList(Book(), Book(), Book())
    

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)


        // Get the view model
        val viewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        // Set the item click listener
        val listener : (Book) -> Unit = { book ->
            viewModel.setSelected(book.id)
        }

        val adapter = BookAdapter(books, listener)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager= LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = adapter
        }

        // Observe data changes and update adapter
        viewModel.books.observe(this, Observer { newBooks ->
            newBooks.let { adapter.setData(newBooks!!) }
        })


        return view
    }

}
