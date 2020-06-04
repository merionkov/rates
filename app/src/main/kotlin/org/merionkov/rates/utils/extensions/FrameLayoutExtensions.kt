package org.merionkov.rates.utils.extensions

import android.view.View
import android.widget.FrameLayout
import org.merionkov.rates.utils.IndexBasedIterable

val FrameLayout.childs: Iterable<View>
    get() = IndexBasedIterable(
        getItemsCount = ::getChildCount,
        getItemAt = ::getChildAt
    )

fun <T : View> FrameLayout.setOnlyVisibleView(visibleView: T) {
    var wasVisibleViewFound = false
    childs.forEach { view ->
        view.visibility = if (view == visibleView) {
            wasVisibleViewFound = true
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
    if (!wasVisibleViewFound) {
        throw RuntimeException("Target visible view not found")
    }
}
