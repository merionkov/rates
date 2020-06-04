package org.merionkov.rates.utils.extensions

fun Float.format(digitsNumber: Int): String {
    return "%.${digitsNumber}f".format(this)
}
