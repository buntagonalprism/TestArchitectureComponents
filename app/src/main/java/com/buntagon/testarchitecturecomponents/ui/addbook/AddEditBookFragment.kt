package com.buntagon.testarchitecturecomponents.ui.addbook

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.ui.MainActivityViewModel
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import kotlinx.android.synthetic.main.fragment_add_edit_book.*
import android.widget.ArrayAdapter
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails


/**
 * A simple [Fragment] subclass for editing the details of a book
 */
class AddEditBookFragment : Fragment() {

    private var mSelectedAuthor : AuthorDetails? = null
    private var book = BookDetails()
    private var mViewModel : LibraryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the view model
        mViewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        mViewModel?.getSelected()?.observe(this, Observer { editBook ->
            editBook?.let {
                book = editBook
                et_title.setText(book.title)
                et_author.setText(book.author)
                et_description.setText(book.description)
                et_publisher.setText(book.published.toString())
                setTitle(R.string.title_book_edit)
            }
        })

        mViewModel?.authors?.observe(this, Observer { authors ->
            authors?.let {
                val adapter = ArrayAdapter<String>(activity,
                        android.R.layout.simple_dropdown_item_1line,
                        authors.map { author -> author.name })
                et_author.setAdapter(adapter)

                et_author.setOnItemClickListener { _, _, position, _ ->
                    mSelectedAuthor = authors[position]
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_add_edit_book, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_book_create)

        bt_save.setOnClickListener {
            book.title = et_title.text.toString()
            book.author = et_author.text.toString()
            book.description = et_description.text.toString()
            book.author = mSelectedAuthor?.name ?: ""
            book.authorId = mSelectedAuthor?.id ?: ""
            mViewModel?.saveBook(book)
        }
    }


    private fun setTitle(titleResource: Int) {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(titleResource)
    }
}
