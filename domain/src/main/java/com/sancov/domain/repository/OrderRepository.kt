package com.sancov.domain.repository

import com.sancov.domain.model.EmptyState
import com.sancov.domain.model.Order
import com.sancov.domain.model.State
import com.sancov.domain.model.Status
import kotlinx.coroutines.flow.Flow

typealias OrderState = State<Order?>
typealias OrdersState = State<List<Order>>

interface OrderRepository {
    fun createOrder(): Flow<EmptyState>
    fun updateOrderStatus(orderId: Long, status: Status): Flow<EmptyState>

    fun getOrder(orderId: Long): Flow<OrderState>
    fun getOrders(): Flow<OrdersState>
}