package com.buntagon.testarchitecturecomponents.ui.addbook

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.R.id.*
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.BookWithAuthor
import com.buntagon.testarchitecturecomponents.data.util.ObjectStringAdapter
import com.buntagon.testarchitecturecomponents.ui.MainActivityViewModel
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import com.buntagon.testarchitecturecomponents.util.replaceAll
import kotlinx.android.synthetic.main.fragment_add_edit_book.*


/**
 * A simple [Fragment] subclass for editing the details of a book
 */
class AddEditBookFragment : Fragment() {

//    private var mSelectedAuthorName: String? = null
//    private var mSelectedAuthorId: String? = null
    private var book = BookWithAuthor()
    private var mViewModel : LibraryViewModel? = null
    private lateinit var mAdapter : ArrayAdapter<String>
    private var mAuthors : List<AuthorDetails> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the view model
        mViewModel = ViewModelProviders.of(activity).get(LibraryViewModel::class.java)

        mViewModel?.getSelected()?.observe(this, Observer { editBook ->
            editBook?.let {
                book = editBook
                et_title.setText(book.title)
                et_description.setText(book.description)
                et_author.setText(book.authorName)
//                mSelectedAuthorId = book.authorId
//                mSelectedAuthorName = book.authorName
                et_publisher.setText(book.published.toString())
                setTitle(R.string.title_book_edit)
            }
        })

        mAdapter = ArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line)

        mViewModel?.authors?.observe(this, Observer { authors ->
                mAuthors = authors ?: ArrayList()
                mAdapter.replaceAll(mAuthors.map { author -> author.name })
//                et_author.setOnItemClickListener { _, _, position, _ ->
//                    val selectedAuthor = adapter.getObjectItem(position)
//                    selectedAuthor?.let {
//                        mSelectedAuthorId = selectedAuthor.id
//                        mSelectedAuthorName = selectedAuthor.name
//                    }
//                }

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

        et_author.setAdapter(mAdapter)

        bt_save.setOnClickListener {
            val author : AuthorDetails? = mAuthors.find { author -> author.name == et_author.text.toString() }
            when {
                et_title.text.toString().length < 3 -> {
                    et_title.error = "Minimum 3 characters"
                }
                author == null -> {
                    et_author.error = "Select a valid author"
                }
                else -> {
                    book.title = et_title.text.toString()
                    book.description = et_description.text.toString()
                    book.authorId = author.id
                    mViewModel?.saveBook(book)
                }
            }
        }
    }


    private fun setTitle(titleResource: Int) {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(titleResource)
    }
}

