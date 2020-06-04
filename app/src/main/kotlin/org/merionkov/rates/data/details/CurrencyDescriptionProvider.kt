package org.merionkov.rates.data.details

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader

class CurrencyDescriptionProvider(
    private val context: Context,
    private val gson: Gson
) {
    private val descriptions by lazy(::readDescriptions)

    @WorkerThread
    fun queryDescription(currencyCode: String): String? {
        return descriptions[currencyCode]
    }

    /**
     * @return [Map] where [Map.Entry.key] is currency code and [Map.Entry.value] is currency description.
     */
    @WorkerThread
    private fun readDescriptions(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        context.assets.open("currencies-details.json").use { file ->
            gson.newJsonReader(file.reader()).use { json ->
                json.beginArray()
                while (json.hasNext()) {
                    gson.fromJson<CurrencyDetails>(json).let {
                        result[it.code] = it.description
                    }
                }
            }
        }
        return result
    }

    private inline fun <reified T> Gson.fromJson(json: JsonReader): T {
        return fromJson(json, T::class.java)
    }

    data class CurrencyDetails(
        @SerializedName("code") val code: String,
        @SerializedName("description") val description: String
    )
}
