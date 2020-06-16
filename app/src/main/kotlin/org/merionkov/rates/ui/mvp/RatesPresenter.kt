package org.merionkov.rates.ui.mvp

import moxy.MvpPresenter
import org.merionkov.rates.data.CurrenciesRatesFetcher
import org.merionkov.rates.data.CurrencyRateEntity
import org.merionkov.rates.utils.WorkManager
import org.merionkov.rates.utils.extensions.move
import java.io.IOException

private const val UPDATE_DELAY_MS = 1000L
private const val DEFAULT_CURRENCY_CODE = "EUR"
private const val DEFAULT_CURRENCY_RATE = 1f
private const val FETCH_BASE_CURRENCY_CODE = "EUR"

class RatesPresenter(
    private val ratesFetcher: CurrenciesRatesFetcher,
    private val workManager: WorkManager
) : MvpPresenter<RatesView>() {

    @Volatile
    private var baseCurrencyCode = DEFAULT_CURRENCY_CODE

    @Volatile
    private var baseCurrencyRate = DEFAULT_CURRENCY_RATE

    @Volatile
    private var ratesList: List<CurrencyRateEntity>? = null

    @Volatile
    private var ratesMap: Map<String, CurrencyRateEntity>? = null

    override fun onFirstViewAttach() {
        start()
    }

    private fun start() {
        resetState()
        viewState.showProgressBar()
        workManager.repeatOnBackground(UPDATE_DELAY_MS) {
            execute { onNewRatesReceived(ratesFetcher.fetch(FETCH_BASE_CURRENCY_CODE)) }
        }
    }

    @Synchronized
    fun onBaseCurrencyCodeChanged(code: String) = execute {

        if (baseCurrencyCode == code) return@execute

        val currentRatesList = ratesList ?: error("Rates list not yet initialized")

        val baseRateIndex = currentRatesList
            .indexOfFirst { it.code == code }
            .takeIf { it != -1 }
            ?: error("Rates list does not contain required currency rate")

        baseCurrencyCode = code
        baseCurrencyRate = currentRatesList[baseRateIndex].rate

        val update = ListUpdate.ItemMoved(baseRateIndex, 0)

        ratesList = currentRatesList
            .toMutableList()
            .also { it.move(fromPosition = baseRateIndex, toPosition = 0) }
            .also { showRates(it, update) }
    }

    @Synchronized
    fun onBaseCurrencyRateChanged(rate: Float) = execute {

        if (baseCurrencyRate == rate) return@execute

        val currentRatesList = ratesList ?: error("Rates list not yet initialized")
        val currentRatesMap = ratesMap ?: error("Rates map not yet initialized")
        val currentBaseCode = baseCurrencyCode

        val baseRateMultiplier = rate / currentRatesMap.getValue(currentBaseCode).rate

        baseCurrencyRate = rate

        val update = ListUpdate.ItemsUpdated(1 until currentRatesList.size)

        ratesList = currentRatesList
            .map { it.copy(rate = currentRatesMap.getValue(it.code).rate * baseRateMultiplier) }
            .also { showRates(it, update) }
    }

    @Synchronized
    private fun onNewRatesReceived(newRates: List<CurrencyRateEntity>) {
        val actualBaseCurrencyCode = baseCurrencyCode
        val actualBaseCurrencyRate = baseCurrencyRate
        val actualRatesList = ratesList

        val fetchedBaseRate = newRates.firstOrNull { it.code == actualBaseCurrencyCode }
            ?: error("Base currency rate not found in fetched rates")

        val baseRateMultiplier = actualBaseCurrencyRate / fetchedBaseRate.rate

        val newRatesMap = newRates.map {
            it.code to it.copy(rate = it.rate * baseRateMultiplier)
        }.toMap()

        val newRatesList = actualRatesList?.mapNotNull { newRatesMap[it.code] } ?: newRates

        val ratesUpdate = if (ratesList != null) {
            ListUpdate.ItemsUpdated(1 until newRatesList.size)
        } else {
            null
        }

        ratesList = newRatesList.also { showRates(it, ratesUpdate) }
        ratesMap = newRatesMap
    }

    private fun showRates(rates: List<CurrencyRateEntity>, ratesUpdate: ListUpdate? = null) {
        workManager.doOnMain { viewState.showRatesList(rates, ratesUpdate) }
    }

    private fun showError(error: Throwable) {
        workManager.doOnMain { viewState.showError(error) }
    }

    private fun resetState() {
        baseCurrencyCode = DEFAULT_CURRENCY_CODE
        baseCurrencyRate = DEFAULT_CURRENCY_RATE
        ratesList = null
        ratesMap = null
    }

    private inline fun execute(action: () -> Unit) {
        try {
            action.invoke()
        } catch (error: IOException) {
            if (ratesList == null) {
                showError(error)
            }
        } catch (error: Throwable) {
            showError(error)
            resetState()
        }
    }

    override fun onDestroy() {
        workManager.clear()
    }
}
