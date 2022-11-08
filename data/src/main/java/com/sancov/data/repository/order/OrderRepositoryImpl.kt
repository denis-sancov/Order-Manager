package com.sancov.data.repository

import com.sancov.data.repository.order.OrderStore
import com.sancov.domain.model.State
import com.sancov.domain.model.Status
import com.sancov.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class OrderRepositoryImpl @Inject constructor(
    private val store: OrderStore
) : OrderRepository {
    override fun createOrder() = flow {
        emit(State.Loading(null))

        store.createOrder()

        emit(State.Success(Unit))
    }

    override fun updateOrderStatus(orderId: Long, status: Status) = flow {
        emit(State.Loading(null))

        store.updateOrderStatus(orderId, status)

        emit(State.Success(Unit))
    }

    override fun getOrder(orderId: Long) = flow {
        emit(State.Loading(null))

        store.getOrder(orderId).collect {
            emit(State.Success(it))
        }
    }

    override fun getOrders() = flow {
        emit(State.Loading(null))

        store.getOrders().collect {
            emit(State.Success(it))
        }
    }
}