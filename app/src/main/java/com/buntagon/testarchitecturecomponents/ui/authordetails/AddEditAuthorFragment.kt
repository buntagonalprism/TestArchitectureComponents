package com.buntagon.testarchitecturecomponents.ui.authordetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.ui.MainActivityViewModel
import com.buntagon.testarchitecturecomponents.ui.authors.AuthorsViewModel
import kotlinx.android.synthetic.main.fragment_add_edit_author.*

/**
 *
 */
class AddEditAuthorFragment : Fragment() {

    private var mAuthor = AuthorDetails()
    private var mViewModel : AuthorsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity).get(AuthorsViewModel::class.java)
        setHasOptionsMenu(true)
        mViewModel?.getSelected()?.observe(this, Observer { author ->
            author?.let {
                setTitle(R.string.title_author_edit)
                mAuthor = author
                setData()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.common_save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_save -> {
                getData()
                mViewModel?.saveAuthor(mAuthor)
                return true
            }
            R.id.menu_delete -> {
                mViewModel?.delete(mAuthor)
                mViewModel?.saveCompleteEvent?.call()
                return true
            }
        }
        return false
    }

    private fun setData() {
        tv_author_name.setText(mAuthor.name)
        tv_author_age.setText(mAuthor.age.toString())
    }

    private fun getData()  {
        mAuthor.name = tv_author_name.text.toString()
        mAuthor.age = tv_author_age.text.toString().toInt()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setTitle(R.string.title_author_create)
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_add_edit_author, container, false)
    }

    private fun setTitle(titleResource: Int) {
        val activityViewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        activityViewModel.activityTitle.value = resources.getString(titleResource)
    }
}
