package org.merionkov.rates.utils.extensions

import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.requestInput() {
    requestFocus()
    context.inputMethodManager.showSoftInput(
        this, InputMethodManager.SHOW_IMPLICIT
    )
}
