package org.merionkov.rates.utils.functions

import android.content.Context
import android.os.Handler
import android.os.HandlerThread

fun createWorkerHandler(name: String): Handler {
    return HandlerThread(name)
        .also(HandlerThread::start)
        .let(HandlerThread::getLooper)
        .let(::Handler)
}

fun createMainHandler(context: Context): Handler {
    return Handler(context.mainLooper)
}
