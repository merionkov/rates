package org.merionkov.rates.utils

class IndexBasedIterable<T>(
    private val getItemsCount: () -> Int,
    private val getItemAt: (index: Int) -> T
) : Iterable<T> {

    override fun iterator() = object : Iterator<T> {

        private var currentItemIndex: Int = -1

        override fun hasNext() = currentItemIndex + 1 < getItemsCount()

        override fun next() = if (hasNext()) {
            getItemAt(++currentItemIndex)
        } else {
            throw NoSuchElementException()
        }
    }
}
