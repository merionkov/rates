package org.merionkov.rates.data.revolut

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutAndroidApi {

    @GET("/api/android/latest")
    fun requestRates(@Query("base") baseCurrencyCode: String): Call<CurrenciesRates>

    data class CurrenciesRates(
        @SerializedName("baseCurrency") val baseCurrencyCode: String,
        @SerializedName("rates") val relativeRates: Map<String, Float>
    )
}
