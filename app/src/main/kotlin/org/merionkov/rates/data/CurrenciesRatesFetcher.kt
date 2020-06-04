package org.merionkov.rates.data

import org.merionkov.rates.data.details.CurrencyDescriptionProvider
import org.merionkov.rates.data.revolut.RevolutAndroidClient

class CurrenciesRatesFetcher(
    private val revolut: RevolutAndroidClient,
    private val descriptions: CurrencyDescriptionProvider
) {
    fun fetch(baseCurrencyCode: String): List<CurrencyRateEntity> {
        return revolut.requestCurrenciesRates(baseCurrencyCode).map { rate ->
            CurrencyRateEntity(
                code = rate.key,
                rate = rate.value,
                description = descriptions.queryDescription(rate.key)
            )
        }
    }
}
