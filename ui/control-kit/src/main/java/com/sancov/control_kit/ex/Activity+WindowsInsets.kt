package com.sancov.control_kit.ex

import android.app.Activity
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun Activity.observeWindowInsets(root: View, listener: (View, Insets) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

        listener(v, insets)

        if (!v.isInLayout) {
            v.requestLayout()
        }

        windowInsets
    }
}