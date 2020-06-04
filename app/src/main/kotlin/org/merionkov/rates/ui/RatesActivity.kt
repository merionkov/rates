package org.merionkov.rates.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.merionkov.rates.R
import org.merionkov.rates.data.CurrencyRateEntity
import org.merionkov.rates.ui.mvp.ListUpdate
import org.merionkov.rates.ui.mvp.RatesView
import org.merionkov.rates.utils.extensions.bindView
import org.merionkov.rates.utils.extensions.services
import org.merionkov.rates.utils.extensions.setOnlyVisibleView
import org.merionkov.rates.utils.functions.unsafeLazy
import java.io.IOException

class RatesActivity : MvpAppCompatActivity(R.layout.a_rates), RatesView {

    private val contentView: FrameLayout by bindView(R.id.ratesContent)
    private val listView: RecyclerView by bindView(R.id.ratesList)
    private val progressBar: View by bindView(R.id.ratesProgressBar)
    private val errorView: TextView by bindView(R.id.ratesError)

    private val presenter by moxyPresenter { services.currenciesPresenter }

    private val listLayoutManager by unsafeLazy {
        LinearLayoutManager(this)
    }

    private val listAdapter by unsafeLazy {
        RatesAdapter(
            ::onBaseCurrencyCodeChanged,
            ::onBaseCurrencyRateChanged
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listView.layoutManager = listLayoutManager
        listView.adapter = listAdapter
        listView.setHasFixedSize(true)
    }

    override fun showProgressBar() {
        contentView.setOnlyVisibleView(progressBar)
    }

    override fun showRatesList(rates: List<CurrencyRateEntity>, ratesUpdate: ListUpdate?) {
        contentView.setOnlyVisibleView(listView)
        listAdapter.submitItems(rates, ratesUpdate)
    }

    override fun showError(error: Throwable) {
        contentView.setOnlyVisibleView(errorView)
        if (error is IOException) {
            errorView.setText(R.string.errorNetwork)
        } else {
            errorView.setText(R.string.errorUnknown)
        }
    }

    private fun onBaseCurrencyCodeChanged(currencyCode: String) {
        presenter.onBaseCurrencyCodeChanged(currencyCode)
    }

    private fun onBaseCurrencyRateChanged(rate: Float) {
        presenter.onBaseCurrencyRateChanged(rate)
    }
}
