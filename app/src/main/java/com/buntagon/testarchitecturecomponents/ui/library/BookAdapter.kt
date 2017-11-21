package com.buntagon.testarchitecturecomponents.ui.library

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Book

/**

 */
class BookAdapter(private var mValues: MutableList<Book>, private val mListener: (Book) -> Unit) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun setData(newBooks: MutableList<Book>) {
        mValues = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = mValues[position]
        holder.mItem = book
        holder.mIdView.text = book.title
        holder.mContentView.text = book.description

        holder.mView.setOnClickListener {
            mListener(book)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mIdView : TextView = mView.findViewById<View>(R.id.id) as TextView
        val mContentView: TextView = mView.findViewById<View>(R.id.content) as TextView
        var mItem: Book? = null

    }
}
