package com.sancov.order.model_ex

import androidx.annotation.StringRes
import com.sancov.domain.model.Status
import com.sancov.orders.R

@get: StringRes
internal val Status.titleResId: Int
    get() = when (this) {
        Status.Ready -> R.string.status_ready
        Status.New -> R.string.status_new
        Status.Preparing -> R.string.status_preparing
        Status.Delivered -> R.string.status_delivered
    }