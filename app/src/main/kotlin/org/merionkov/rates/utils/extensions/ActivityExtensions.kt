package org.merionkov.rates.utils.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import org.merionkov.rates.utils.functions.unsafeLazy

fun <T : View> Activity.bindView(@IdRes id: Int) = unsafeLazy { findViewById<T>(id) }
