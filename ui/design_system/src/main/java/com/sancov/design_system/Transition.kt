package com.sancov.design_system

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import com.sancov.ui.design_system.R

open class Transition(
    @AnimatorRes @AnimRes val enter: Int,
    @AnimatorRes @AnimRes val exit: Int,
    @AnimatorRes @AnimRes val popEnter: Int,
    @AnimatorRes @AnimRes val popExit: Int,
) {
    object Push : Transition(
        R.anim.push_enter,
        R.anim.push_exit,
        R.anim.push_pop_enter,
        R.anim.push_pop_exit
    )

    object None : Transition(0, 0, 0, 0)

    val dropEnterAnimation: Transition
        get() {
            return Transition(0, 0, popEnter, popExit)
        }

    val dropExitAnimation: Transition
        get() {
            return Transition(enter, 0, popEnter, popExit)
        }
}