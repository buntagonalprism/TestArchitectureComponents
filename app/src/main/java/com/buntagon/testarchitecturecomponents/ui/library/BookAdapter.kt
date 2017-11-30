package com.buntagon.testarchitecturecomponents.ui.library

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buntagon.testarchitecturecomponents.R
import com.buntagon.testarchitecturecomponents.data.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

/**

 */
class BookAdapter(private val mSelectListener: (Book) -> Unit, private val mDeleteListener: (Book) -> Unit) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private var mValues: List<Book> = ArrayList()

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun setData(newBooks: List<Book>) {
        mValues = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = mValues[position]
        holder.bind(book)
    }

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView)  {

        fun bind(book: Book) = with (mView) {
            tv_title.text = book.title
            tv_description.text = book.description
            ll_content.setOnClickListener {
                mSelectListener(book)
            }
            iv_delete.setOnClickListener{
                mDeleteListener(book)
            }
        }

    }
}
