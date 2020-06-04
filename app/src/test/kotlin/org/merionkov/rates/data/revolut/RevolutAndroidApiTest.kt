package org.merionkov.rates.data.revolut

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.not
import org.junit.Test
import org.merionkov.rates.utils.constants.serviceLocator

class RevolutAndroidApiTest {

    private val api = serviceLocator.revolutApi

    @Test
    fun shouldBaseCurrencyBeCorrectInResponse() {
        assertThat(requestRates("EUR").baseCurrencyCode, equalTo("EUR"))
    }

    @Test
    fun shouldRatesDoNotContainBaseCurrency() {
        assertThat(requestRates("EUR").relativeRates, not(hasKey("EUR")))
    }

    @Test
    fun shouldRatesContainCommonCurrencies() {
        requestRates("JPY").relativeRates.let {
            assertThat(it, hasKey("USD"))
            assertThat(it, hasKey("EUR"))
            assertThat(it, hasKey("RUB"))
        }
    }

    private fun requestRates(baseCurrencyCode: String): RevolutAndroidApi.CurrenciesRates {
        return api.requestRates(baseCurrencyCode).execute().body()!!
    }
}
