package org.merionkov.rates.utils.extensions

fun <T> MutableList<T>.move(fromPosition: Int, toPosition: Int) {
    val item = get(fromPosition)
    removeAt(fromPosition)
    add(toPosition, item)
}
