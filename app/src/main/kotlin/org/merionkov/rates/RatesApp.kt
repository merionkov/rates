package org.merionkov.rates

import android.app.Application
import org.merionkov.rates.factories.AndroidServiceLocator

class RatesApp : Application() {
    val serviceLocator by lazy { AndroidServiceLocator(this) }
}
