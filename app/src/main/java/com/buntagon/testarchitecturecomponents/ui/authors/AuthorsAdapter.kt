package com.buntagon.testarchitecturecomponents.ui.authors

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import kotlinx.android.synthetic.main.item_author.view.*

/**
 * Display a list of authors
 */
class AuthorsAdapter(private val mListener: (AuthorDetails) -> Unit) : RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {

    private val mValues = ArrayList<AuthorDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_author, parent, false)
        return ViewHolder(view)
    }

    fun setData(newData: List<AuthorDetails>?) {
        newData?.let {
            mValues.clear()
            mValues.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

        fun bind(author: AuthorDetails) = with(mView) {
            author_name.text = author.name
            author_age.text = author.age.toString()
            setOnClickListener {
                mListener(author)
            }
        }
    }
}
