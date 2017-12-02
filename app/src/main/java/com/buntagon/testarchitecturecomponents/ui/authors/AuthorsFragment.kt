package com.buntagon.testarchitecturecomponents.ui.authors

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.ui.MainActivityViewModel
import com.buntagon.testarchitecturecomponents.util.shortToast
import kotlinx.android.synthetic.main.fragment_authors.*

/**
 * A fragment representing a list of Authors
 */
class AuthorsFragment : Fragment() {

    private var mViewModel : AuthorsViewModel? = null
    private var mAdapter : AuthorsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the view model
        mViewModel = ViewModelProviders.of(activity).get(AuthorsViewModel::class.java)

        mAdapter = AuthorsAdapter { author ->
            mViewModel?.editAuthor(author.id)
        }

        mViewModel?.authors?.observe(this, Observer { authors ->
            mAdapter?.setData(authors)
        })

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_authors, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_authors.layoutManager = LinearLayoutManager(view?.context)
        rv_authors.adapter = mAdapter

        fab_add_author.setOnClickListener {
            mViewModel?.createAuthor()
        }

        setTitle()
    }

    private fun setTitle() {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(R.string.title_authors_list)
    }



}
