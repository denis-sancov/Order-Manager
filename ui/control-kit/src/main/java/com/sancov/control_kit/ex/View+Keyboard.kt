package com.sancov.control_kit.ex

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.resignKeyboard() {
    val ctx = context
    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.hideSoftInputFromWindow(windowToken, 0)
    clearFocus()
}