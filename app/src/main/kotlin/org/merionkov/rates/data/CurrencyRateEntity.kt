package org.merionkov.rates.data

data class CurrencyRateEntity(
    val code: String,
    val description: String?,
    val rate: Float
)
