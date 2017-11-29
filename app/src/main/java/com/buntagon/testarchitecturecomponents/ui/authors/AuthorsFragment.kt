package com.buntagon.testarchitecturecomponents.ui.authors

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Author
import com.buntagon.testarchitecturecomponents.ui.library.LibraryViewModel
import com.buntagon.testarchitecturecomponents.util.shortToast

/**
 * A fragment representing a list of Authors
 */
class AuthorsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_authors, container, false)

        // Get the view model
        val viewModel = ViewModelProviders.of(activity).get(AuthorsViewModel::class.java)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = AuthorsAdapter(viewModel.authors.value!!) { author ->
                activity.shortToast("Pressed author: " + author.name)
            }
        }
        return view
    }


}
