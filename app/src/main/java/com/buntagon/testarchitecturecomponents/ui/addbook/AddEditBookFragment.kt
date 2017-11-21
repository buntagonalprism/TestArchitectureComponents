package com.buntagon.testarchitecturecomponents.ui.addbook

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import kotlinx.android.synthetic.main.fragment_add_edit_book.*

/**
 * A simple [Fragment] subclass for editing the details of a book
 */
class AddEditBookFragment : Fragment() {

    private var book = Book()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_add_edit_book, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the view model
        val viewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        viewModel.getSelected().observe(this, Observer { editBook ->
            editBook?.let {
                book = editBook
                et_title.setText(book.title)
                et_author.setText(book.author)
                et_description.setText(book.description)
                et_publisher.setText(book.published.toString())
            }
        })

        bt_save.setOnClickListener {
            book.title = et_title.text.toString()
            book.author = et_author.text.toString()
            book.description = et_description.text.toString()
            viewModel.saveBook(book)
        }
    }
}
