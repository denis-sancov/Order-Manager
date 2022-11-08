package com.sancov.control_kit.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.sancov.control_kit.BuildConfig
import com.sancov.control_kit.R
import com.sancov.control_kit.ex.resolveColor
import com.sancov.design_system.ThemeColor

abstract class AnyFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    private var inflationTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inflationTime = System.currentTimeMillis()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.also {
            it.fitsSystemWindows = false

            it.elevation = resources.getDimension(R.dimen.elevation_fragment)
            it.isFocusable = true
            it.isFocusableInTouchMode = true
            it.outlineProvider = null

            if (it.background == null) {
                it.setBackgroundColor(it.context.resolveColor(ThemeColor.Background.Primary))
            }
        }

        val result = System.currentTimeMillis() - inflationTime

        if (BuildConfig.DEBUG) {
            Log.d("PERFORMANCE", "FRAGMENT ${this::class.java.canonicalName} = $result ms")
        }
    }
}