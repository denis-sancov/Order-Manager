package com.sancov.order

import androidx.fragment.app.FragmentManager
import com.sancov.control_kit.ex.push
import com.sancov.order.details.OrderDetailsFragment
import com.sancov.order.list.OrderListFragment
import com.sancov.routers.OrderRouter
import javax.inject.Inject

class OrderRouterImpl @Inject constructor() : OrderRouter {
    override fun toOrders(fm: FragmentManager, animated: Boolean) {
        fm.push(OrderListFragment(), animated)
    }

    override fun toOrder(fm: FragmentManager, orderId: Long, animated: Boolean) {
        fm.push(OrderDetailsFragment.instantiate(orderId), animated)
    }

}