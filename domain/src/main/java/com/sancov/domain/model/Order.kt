package com.sancov.domain.model

import java.time.Instant

enum class Status {
    New, Preparing, Ready, Delivered;

    fun resolveNextStatus() = when (this) {
        New -> Preparing
        Preparing -> Ready
        Ready -> Delivered
        Delivered -> Delivered
    }
}

interface Order {
    val id: Long
    val status: Status
    val openDate: Instant
    val deliveredDate: Instant?
}