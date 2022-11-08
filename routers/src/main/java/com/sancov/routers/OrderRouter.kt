package com.sancov.routers

import androidx.fragment.app.FragmentManager

interface OrderRouter {
    fun toOrders(fm: FragmentManager, animated: Boolean = true)
    fun toOrder(fm: FragmentManager, orderId: Long, animated: Boolean = true)
}