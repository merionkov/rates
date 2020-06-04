package org.merionkov.rates.factories

import android.content.Context
import com.google.gson.Gson
import org.merionkov.rates.data.CurrenciesRatesFetcher
import org.merionkov.rates.data.details.CurrencyDescriptionProvider
import org.merionkov.rates.ui.mvp.RatesPresenter
import org.merionkov.rates.utils.WorkManager

class AndroidServiceLocator(private val context: Context) {

    private val serviceLocator = ServiceLocator()

    val iconsResolver get() = serviceLocator.iconsResolver

    val worker get() = WorkManager(context)

    val currencyDescriptionProvider by lazy {
        CurrencyDescriptionProvider(context, Gson())
    }

    val ratesFetcher by lazy {
        CurrenciesRatesFetcher(serviceLocator.revolutClient, currencyDescriptionProvider)
    }

    val currenciesPresenter get() = RatesPresenter(ratesFetcher, worker)
}
