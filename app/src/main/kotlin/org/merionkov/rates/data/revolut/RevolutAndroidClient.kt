package org.merionkov.rates.data.revolut

import org.merionkov.rates.utils.extensions.perform

class RevolutAndroidClient(private val api: RevolutAndroidApi) {
    /**
     * @param baseCurrencyCode currency code, rate of which will be 1.
     * @return [Map] where [Map.Entry.key] is currency code and [Map.Entry.value] is currency rate.
     */
    fun requestCurrenciesRates(baseCurrencyCode: String): Map<String, Float> {
        return api.requestRates(baseCurrencyCode)
            .perform()
            .relativeRates
            .plus(baseCurrencyCode to 1f)
    }
}
