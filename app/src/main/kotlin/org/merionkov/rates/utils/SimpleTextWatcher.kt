package org.merionkov.rates.utils

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
}
