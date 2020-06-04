package org.merionkov.rates.utils.extensions

import android.content.Context
import org.merionkov.rates.data.CurrencyRateEntity

val CurrencyRateEntity.regionCode get() = code.substring(0, 2)

fun CurrencyRateEntity.getIconUrl(context: Context): String {
    return context.services.iconsResolver.resolveIconUrl(regionCode)
}
