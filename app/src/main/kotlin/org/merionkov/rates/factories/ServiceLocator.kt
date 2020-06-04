package org.merionkov.rates.factories

import org.merionkov.rates.data.icons.RegionsIconsResolver
import org.merionkov.rates.data.revolut.RevolutAndroidApi
import org.merionkov.rates.data.revolut.RevolutAndroidClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REVOLUT_BASE_URL = "https://hiring.revolut.codes/"
private const val ICONS_STYLE = "flat"
private const val ICONS_SIZE = 64

open class ServiceLocator {

    val iconsResolver by lazy {
        RegionsIconsResolver(
            ICONS_STYLE,
            ICONS_SIZE
        )
    }

    val revolutApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(REVOLUT_BASE_URL)
            .build()
            .create(RevolutAndroidApi::class.java)
    }

    val revolutClient by lazy {
        RevolutAndroidClient(revolutApi)
    }
}
