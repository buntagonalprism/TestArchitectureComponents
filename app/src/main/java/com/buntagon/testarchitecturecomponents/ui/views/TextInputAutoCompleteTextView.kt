package com.buntagon.testarchitecturecomponents.ui.views

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.inputmethod.InputConnection
import android.view.inputmethod.EditorInfo
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.util.AttributeSet


/**
 * Basic implementation of the auto complete text view allowing it to be used inside a text input
 * layout
 *
 * Created by Alex on 2/12/2017.
 */
class TextInputAutoCompleteTextView : AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(outAttrs)
        if (ic != null && outAttrs.hintText == null) {
            // If we don't have a hint and our parent is a TextInputLayout, use it's hint for the
            // EditorInfo. This allows us to display a hint in 'extract mode'.
            val parent = parent
            if (parent is TextInputLayout) {
                outAttrs.hintText = parent.hint
            }
        }
        return ic
    }
}