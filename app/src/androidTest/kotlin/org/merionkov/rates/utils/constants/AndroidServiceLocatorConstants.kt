package org.merionkov.rates.utils.constants

import androidx.test.platform.app.InstrumentationRegistry
import org.merionkov.rates.factories.AndroidServiceLocator

val androidServiceLocator by lazy {
    AndroidServiceLocator(InstrumentationRegistry.getInstrumentation().targetContext)
}
