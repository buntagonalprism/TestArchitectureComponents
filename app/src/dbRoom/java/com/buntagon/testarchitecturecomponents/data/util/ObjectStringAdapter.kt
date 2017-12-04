package com.buntagon.testarchitecturecomponents.data.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

/**
 * Adapter for an array of objects which can be mapped to strings, e.g. for display in an
 * autocomplete text view. Primary adavantage is that an onItemClickListener on the auto-complete
 * text view can get a reference to the selected object
 *
 * Created by Alex on 5/12/2017.
 */

class ObjectStringAdapter<T>(context: Context, private val rowResource: Int, private val data: List<T>, private val getName: (T) -> String) : ArrayAdapter<String>(context, rowResource) {

    private val inflater = LayoutInflater.from(context)
    private var filteredData : List<T> = data

    override fun getFilter(): Filter {
        return ObjectFilter(this, data, getName)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(rowResource, parent, false)
        if (view is TextView) {
            view.text = getName(filteredData[position])
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return filteredData.size
    }

    override fun getItem(position: Int): String {
        return getName(filteredData[position])
    }

    fun getObjectItem(position: Int) : T? = if (position < filteredData.size) filteredData[position] else null

    private class ObjectFilter<T>(val objectAdapter: ObjectStringAdapter<T>, val data: List<T>, val getName: (T) -> String) : Filter() {
        override fun performFiltering(prefixString: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (prefixString == null || prefixString.count() == 0) {
                filterResults.count = data.size
                filterResults.values = data
            }
            else {
                val count = data.size
                val newValues = java.util.ArrayList<T>()
                val prefixLower = prefixString.toString().toLowerCase()

                for (i in 0 until count) {
                    val value = data[i]
                    val valueText = getName(value).toLowerCase()

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixLower)) {
                        newValues.add(value)
                    } else {
                        val words = valueText.split(" ").toTypedArray()
                        for (word in words) {
                            if (word.startsWith(prefixLower)) {
                                newValues.add(value)
                                break
                            }
                        }
                    }
                }

                filterResults.values = newValues
                filterResults.count = newValues.size
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
            @Suppress("UNCHECKED_CAST")
            objectAdapter.filteredData = results?.values as List<T>
            if (results.count > 0) {
                objectAdapter.notifyDataSetChanged()
            } else {
                objectAdapter.notifyDataSetInvalidated()
            }
        }
    }


}
