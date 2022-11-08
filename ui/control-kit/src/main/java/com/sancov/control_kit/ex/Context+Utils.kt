package com.sancov.control_kit.ex

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import com.sancov.design_system.AnyColor

val Context.isUsingNightMode: Boolean
    get() {
        val status = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return status == Configuration.UI_MODE_NIGHT_YES
    }

@ColorInt
fun Context.resolveColor(color: AnyColor): Int {
    return ResourcesCompat.getColor(resources, color.resId, null)
}

@ColorInt
fun Context.resolveColor(@ColorRes resId: Int): Int {
    return ResourcesCompat.getColor(resources, resId, null)
}