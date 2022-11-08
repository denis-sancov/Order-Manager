package com.sancov.data.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

fun timerFlow(durationSeconds: Long) = flow {
    while (true) {
        emit(Unit)
        delay(durationSeconds.seconds)
    }
}