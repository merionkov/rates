package org.merionkov.rates.utils.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import org.merionkov.rates.RatesApp
import org.merionkov.rates.factories.AndroidServiceLocator

val Context.app: RatesApp get() = applicationContext as RatesApp

val Context.services: AndroidServiceLocator get() = app.serviceLocator

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
