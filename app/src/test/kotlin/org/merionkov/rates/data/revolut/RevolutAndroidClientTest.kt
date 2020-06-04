package org.merionkov.rates.data.revolut

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.Matchers.hasKey
import org.junit.Test
import org.merionkov.rates.utils.constants.serviceLocator

class RevolutAndroidClientTest {

    private val client = serviceLocator.revolutClient

    @Test
    fun shouldProvidedRatesContainBaseCurrencyRate() {
        assertThat(client.requestCurrenciesRates("USD"), hasEntry("USD", 1f))
    }

    @Test
    fun shouldProvideCommonCurrenciesRates() {
        client.requestCurrenciesRates("JPY").let {
            assertThat(it, hasKey("USD"))
            assertThat(it, hasKey("EUR"))
            assertThat(it, hasKey("RUB"))
        }
    }
}
