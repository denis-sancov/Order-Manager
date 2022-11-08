package com.sancov.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.sancov.control_kit.ex.refreshSystemBarStyle
import com.sancov.control_kit.fragment.RouterFragment
import com.sancov.routers.OrderRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(R.layout.dashboard_activity) {
    @Inject
    lateinit var orderRouter: OrderRouter

    private val appRouterFragment: RouterFragment
        get() {
            return supportFragmentManager.findFragmentById(R.id.app_router_fragment) as RouterFragment
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        if (savedInstanceState == null) {
            orderRouter.toOrders(
                appRouterFragment.childFragmentManager,
                animated = false
            )

            appRouterFragment.executePending()
        }

        appRouterFragment.refreshSystemBarStyle()
    }
}