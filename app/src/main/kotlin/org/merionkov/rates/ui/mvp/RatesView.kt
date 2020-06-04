package org.merionkov.rates.ui.mvp

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import org.merionkov.rates.data.CurrencyRateEntity

interface RatesView : MvpView {

    @SingleState
    fun showProgressBar()

    @SingleState
    fun showRatesList(rates: List<CurrencyRateEntity>, ratesUpdate: ListUpdate?)

    @SingleState
    fun showError(error: Throwable)
}
