package org.merionkov.rates.data.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Test
import org.junit.runner.RunWith
import org.merionkov.rates.utils.constants.androidServiceLocator

@RunWith(AndroidJUnit4::class)
class CurrencyDescriptionProviderTest {

    private val provider = androidServiceLocator.currencyDescriptionProvider

    @Test
    fun shouldProvideDetailsForCommonCurrencies() {
        assertThat(provider.queryDescription("USD"), not(isEmptyOrNullString()))
        assertThat(provider.queryDescription("EUR"), not(isEmptyOrNullString()))
        assertThat(provider.queryDescription("RUB"), not(isEmptyOrNullString()))
    }

    @Test
    fun shouldNotProvideDetailsForUnknownCurrencies() {
        assertThat(provider.queryDescription("UKNWN"), nullValue())
    }
}
