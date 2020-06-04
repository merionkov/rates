package org.merionkov.rates.utils.extensions

import retrofit2.Call

fun <T> Call<T>.perform(): T = execute().body() ?: error("Response has no body")
