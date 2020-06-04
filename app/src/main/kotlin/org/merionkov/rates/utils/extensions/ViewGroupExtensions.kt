package org.merionkov.rates.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layout: Int, attachImmediately: Boolean): View {
    return LayoutInflater.from(context).inflate(layout, this, attachImmediately)
}
