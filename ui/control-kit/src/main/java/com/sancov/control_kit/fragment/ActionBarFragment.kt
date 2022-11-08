package com.sancov.control_kit.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.android.material.appbar.MaterialToolbar
import com.sancov.control_kit.R
import com.sancov.control_kit.ex.pop

abstract class ActionBarFragment(@LayoutRes contentLayoutId: Int) : AnyFragment(contentLayoutId) {
    lateinit var toolbar: MaterialToolbar
        private set

    private var actionBar: ActionBar? = null

    fun setTitle(@StringRes titleResId: Int) {
        val bar = actionBar ?: return

        if (titleResId == -1) {
            bar.title = " "
        } else {
            bar.setTitle(titleResId)
        }
    }

    fun setTitle(title: String?) {
        actionBar?.title = title ?: " "
    }

    fun setSubtitle(subtitle: String?) {
        actionBar?.subtitle = subtitle
    }

    fun setStartToolbarIcon(@DrawableRes drawable: Int) {
        val bar = actionBar ?: return

        if (drawable == -1) {
            bar.setDisplayHomeAsUpEnabled(false)
            bar.setHomeAsUpIndicator(null)
        } else {
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setHomeAsUpIndicator(drawable)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)

        if (toolbar.isGone) {
            return
        }

        val activity = requireActivity() as AppCompatActivity

        activity.setSupportActionBar(toolbar)

        val bar = activity.supportActionBar as ActionBar
        bar.setDisplayUseLogoEnabled(false)

        val fm = parentFragmentManager

        if (fm.backStackEntryCount > 1) {
            bar.setDisplayHomeAsUpEnabled(true)

            toolbar.setNavigationOnClickListener {
                fm.pop()
            }
        }

        actionBar = bar
    }

    override fun onPause() {
        super.onPause()

        toolbar.dismissPopupMenus()
    }
}