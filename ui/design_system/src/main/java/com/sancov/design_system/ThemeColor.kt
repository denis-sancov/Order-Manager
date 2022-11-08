package com.sancov.design_system

import androidx.annotation.ColorRes
import com.sancov.ui.design_system.R

interface AnyColor {
    @get: ColorRes
    val resId: Int
}

object ThemeColor {
    enum class Background : AnyColor {
        Primary,
        Tertiary;

        override val resId: Int
            get() = when (this) {
                Primary -> R.color.background_primary
                Tertiary -> R.color.background_secondary
            }
    }

    enum class Solid : AnyColor {
        White, Transparent;

        override val resId: Int
            get() = when (this) {
                White -> R.color.solid_white
                Transparent -> android.R.color.transparent
            }
    }

    enum class Content : AnyColor {
        Normal, Opacity50, Opacity30, Opacity10;

        override val resId: Int
            get() = when (this) {
                Normal -> R.color.content
                Opacity50 -> R.color.content_50
                Opacity30 -> R.color.content_30
                Opacity10 -> R.color.content_10
            }
    }

    enum class SystemMenu : AnyColor {
        Background, Border, Fallback;

        override val resId: Int
            get() = when (this) {
                Background -> R.color.system_menu_background
                Border -> R.color.system_menu_border
                Fallback -> R.color.system_menu_fallback
            }
    }
}