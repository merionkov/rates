package org.merionkov.rates.data.icons

import android.annotation.SuppressLint

private const val URL_TEMPLATE = "https://www.countryflags.io/%s/%s/%s.png"

class RegionsIconsResolver(
    private val iconsStyle: String,
    private val iconsSize: Int
) {
    @SuppressLint("DefaultLocale")
    fun resolveIconUrl(regionCode: String): String {
        return URL_TEMPLATE.format(regionCode, iconsStyle, iconsSize)
    }
}
