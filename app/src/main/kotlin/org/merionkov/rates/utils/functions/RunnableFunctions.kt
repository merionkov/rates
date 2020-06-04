package org.merionkov.rates.utils.functions

fun runnable(action: (Runnable) -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            action.invoke(this)
        }
    }
}
