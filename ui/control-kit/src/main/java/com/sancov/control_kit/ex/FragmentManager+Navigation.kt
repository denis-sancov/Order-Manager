package com.sancov.control_kit.ex

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sancov.control_kit.R
import com.sancov.design_system.Transition

enum class ModalityBehaviour {
    Replace, Add
}

val FragmentManager.current: Fragment?
    get() {
        return fragments.lastOrNull()
    }

fun FragmentManager.push(fragment: Fragment, animated: Boolean = true) {
    var animation: Transition? = null

    if (animated) {
        animation = Transition.Push
    }

    present(fragment, animation)
}

fun FragmentManager.present(
    fragment: Fragment,
    transition: Transition? = null,
    modalityBehaviour: ModalityBehaviour? = null
) {
    val actions = {
        val defaultModalityBehaviour = ModalityBehaviour.Replace
        var animation: Transition = Transition.Push

        if (transition != null) {
            animation = transition
        }

        show(
            fragment,
            modalityBehaviour = modalityBehaviour ?: defaultModalityBehaviour,
            animation = animation
        )
    }

    if (current != null) {
        current?.resignKeyboard(actions)
    } else {
        actions()
    }
}

fun FragmentManager.show(
    fragment: Fragment,
    @IdRes containerId: Int = R.id.container,
    modalityBehaviour: ModalityBehaviour = ModalityBehaviour.Replace,
    animation: Transition? = null,
) {
    val tag = generateTagFor(fragment::class.java)

    val transaction = beginTransaction()

    animation?.let {
        transaction.setCustomAnimations(it.enter, it.exit, it.popEnter, it.popExit)
    }

    if (modalityBehaviour == ModalityBehaviour.Add) {
        transaction.add(containerId, fragment, tag)
    } else {
        transaction.replace(containerId, fragment, tag)
    }

    transaction
        .setReorderingAllowed(true)
        .addToBackStack(tag)
        .commit()

    fragment.refreshSystemBarStyle()
}


inline fun FragmentManager.pop(
    tag: String? = null,
    inclusive: Boolean = false,
    preserveRoot: Boolean = true,
    crossinline lambda: () -> Unit = {}
) {
    val current = current ?: return

    val target = if (tag != null) {
        findFragmentByTag(tag)
    } else {
        current.parentFragment
    }

    current.resignKeyboard {
        if (preserveRoot && backStackEntryCount == 1) {
            lambda()
            return@resignKeyboard
        }

        val flag = if (inclusive) {
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        } else {
            0
        }
        popBackStack(tag, flag)

        target?.refreshSystemBarStyle()

        val cb = object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)

                lambda()

                fm.unregisterFragmentLifecycleCallbacks(this)
            }
        }

        registerFragmentLifecycleCallbacks(cb, false)
    }
}

inline fun <reified T : Fragment> FragmentManager.popTo(
    inclusive: Boolean = false,
    crossinline lambda: () -> Unit = {}
) {
    val fragment = fragmentByClass<T>() ?: return
    pop(fragment.tag, inclusive, false, lambda)
}

inline fun FragmentManager.popAll(
    inclusive: Boolean = false,
    preserveRoot: Boolean = false,
    crossinline lambda: () -> Unit = {}
) {
    if (backStackEntryCount == 0) {
        return
    }

    val tag = getBackStackEntryAt(0).name

    pop(tag, inclusive, preserveRoot, lambda)
}

inline fun <reified T : Fragment> FragmentManager.fragmentByClass(): T? {
    val clazz = T::class.java

    for (i in 0 until backStackEntryCount) {
        val entry = getBackStackEntryAt(i)

        val candidate = findFragmentByTag(entry.name) ?: continue

        if (clazz.isInstance(candidate)) {
            return candidate as? T
        }
    }
    return null
}

private fun <T : Fragment> FragmentManager.generateTagFor(clazz: Class<T>): String {
    val active = fragments.filterIsInstance(clazz)
    return "${clazz.canonicalName}_idx_${active.size}"
}