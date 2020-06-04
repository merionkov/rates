package org.merionkov.rates.utils

import android.content.Context
import org.merionkov.rates.utils.functions.createMainHandler
import org.merionkov.rates.utils.functions.createWorkerHandler
import org.merionkov.rates.utils.functions.runnable

private const val COMMON_WORKER_NAME = "worker"

class WorkManager(context: Context) {
    private val mainHandler = createMainHandler(context)
    private val workerHandler = createWorkerHandler(COMMON_WORKER_NAME)

    fun doOnMain(action: () -> Unit) {
        mainHandler.post(action)
    }

    fun repeatOnBackground(delayMs: Long, action: () -> Unit) {
        workerHandler.post(runnable {
            action.invoke()
            workerHandler.postDelayed(it, delayMs)
        })
    }

    fun clear() {
        mainHandler.removeCallbacksAndMessages(null)
        workerHandler.removeCallbacksAndMessages(null)
    }
}
