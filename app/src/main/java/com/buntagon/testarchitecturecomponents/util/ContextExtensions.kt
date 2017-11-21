package com.buntagon.testarchitecturecomponents.util

import android.content.Context
import android.widget.Toast

/**
 * Extension functions for android context classes
 * Created by Alex on 21/11/2017.
 */
fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}