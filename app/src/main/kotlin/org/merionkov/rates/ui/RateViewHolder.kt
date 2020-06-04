package org.merionkov.rates.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.merionkov.rates.R
import org.merionkov.rates.data.CurrencyRateEntity
import org.merionkov.rates.utils.extensions.format
import org.merionkov.rates.utils.extensions.getIconUrl
import org.merionkov.rates.utils.extensions.inflate
import org.merionkov.rates.utils.extensions.requestInput
import org.merionkov.rates.utils.functions.textWatcher

private const val TAG_IS_UNDER_EDIT = "under_edit"

class RateViewHolder(
    parent: ViewGroup,
    private val baseCurrencyCodeListener: (String) -> Unit,
    private val baseCurrencyRateListener: (Float) -> Unit
) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.i_rates, attachImmediately = false)
) {
    private val iconView: ImageView = itemView.findViewById(R.id.currencyIcon)
    private val codeView: TextView = itemView.findViewById(R.id.currencyCode)
    private val descriptionView: TextView = itemView.findViewById(R.id.currencyDescription)
    private val rateView: EditText = itemView.findViewById(R.id.currencyRate)

    private var currentRateCode: String? = null

    init {
        itemView.setOnClickListener {
            baseCurrencyCodeListener.invoke(currentRateCode!!)
            rateView.requestInput()
        }
        rateView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                baseCurrencyCodeListener.invoke(currentRateCode!!)
            }
        }
        rateView.addTextChangedListener(textWatcher { text ->
            if (rateView.tag != TAG_IS_UNDER_EDIT) {
                baseCurrencyRateListener.invoke(text.toFloatOrNull() ?: 0f)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun bind(entity: CurrencyRateEntity) {
        loadIcon(entity.getIconUrl(itemView.context))
        updateContent(entity.code, description = entity.description ?: entity.code)
        updateRate(entity.rate)
    }

    private fun updateContent(code: String, description: String) {
        if (currentRateCode != code) {
            currentRateCode = code
            codeView.text = code
            descriptionView.text = description
        }
    }

    private fun updateRate(rate: Float) {
        if (!rateView.hasFocus()) {
            rateView.tag = TAG_IS_UNDER_EDIT
            rateView.setText(rate.format(2))
            rateView.tag = null
        }
    }

    private fun loadIcon(url: String) {
        Glide.with(iconView)
            .load(url)
            .placeholder(R.drawable.ic_currency)
            .error(R.drawable.ic_currency)
            .into(iconView)
    }
}
