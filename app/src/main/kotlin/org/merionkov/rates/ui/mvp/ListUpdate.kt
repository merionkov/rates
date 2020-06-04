package org.merionkov.rates.ui.mvp

sealed class ListUpdate {

    class ItemsUpdated(val range: IntRange) : ListUpdate()

    class ItemMoved(val fromPosition: Int, val toPosition: Int) : ListUpdate()
}
