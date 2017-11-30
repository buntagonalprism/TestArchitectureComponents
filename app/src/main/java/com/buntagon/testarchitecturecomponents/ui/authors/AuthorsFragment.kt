package com.buntagon.testarchitecturecomponents.ui.authors

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_authors, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the view model
        val viewModel = ViewModelProviders.of(activity).get(AuthorsViewModel::class.java)

        rv_authors.layoutManager = LinearLayoutManager(view?.context)
        rv_authors.adapter = AuthorsAdapter(viewModel.authors.value!!) { author ->
            activity.shortToast("Pressed author: " + author.name)
        }

        setTitle()
    }

    private fun setTitle() {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(R.string.title_authors_list)
    }

}
