package org.merionkov.rates.utils.functions

import android.text.TextWatcher
import org.merionkov.rates.utils.SimpleTextWatcher

fun textWatcher(listener: (String) -> Unit): TextWatcher {
    return object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(s.toString())
        }
    }
}
