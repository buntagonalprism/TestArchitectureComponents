package com.buntagon.testarchitecturecomponents.util

import android.widget.ArrayAdapter

/**
 * Extensions for array adatpers
 * Created by t on 8/12/2017.
 */

/**
 * Replaces all the data in a simple array adapter with a new data set, with only one data set
 * change notification
 */
fun <T> ArrayAdapter<T>.replaceAll(newData: List<T>) {
    // Skip a redundant notification for clearing our list and just re-draw with new list
    this.setNotifyOnChange(false)
    this.clear()
    this.setNotifyOnChange(true)
    this.addAll(newData)
}