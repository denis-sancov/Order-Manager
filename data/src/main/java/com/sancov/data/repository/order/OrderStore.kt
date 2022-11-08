package com.sancov.data.repository.order

import com.sancov.data.utils.timerFlow
import com.sancov.domain.model.Order
import com.sancov.domain.model.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

interface OrderStore {
    suspend fun createOrder()

    suspend fun updateOrderStatus(orderId: Long, status: Status)

    suspend fun getOrder(orderId: Long): Flow<Order?>

    suspend fun getOrders(): Flow<List<Order>>
}

class OrderLocalStoreImpl @Inject constructor() : OrderStore {
    private val _cache = MutableStateFlow(listOf<OrderImpl>())
    private val _scope = CoroutineScope(Dispatchers.IO)

    private var _counter: Long = 1

    init {
        timerFlow(1)
            .onEach {
                removeDeliveredOrders()
            }
            .shareIn(_scope, SharingStarted.Eagerly)
    }

    override suspend fun createOrder() {
        val order = OrderImpl(
            _counter,
            Status.New,
            Instant.now(),
            null
        )

        val list = _cache.value.toMutableList()

        list.add(order)

        _counter += 1L

        _cache.emit(list)
    }

    override suspend fun updateOrderStatus(orderId: Long, status: Status) {
        val list = _cache.value.toMutableList()

        val idx = list.indexOfFirst { it.id == orderId }

        if (idx == -1) {
            return
        }

        val deliveredDate = if (status == Status.Delivered) {
            Instant.now()
        } else {
            null
        }

        list[idx] = list[idx].copy(
            status = status,
            deliveredDate = deliveredDate
        )

        _cache.emit(list)
    }

    override suspend fun getOrder(orderId: Long): Flow<Order?> {
        return _cache.map { list ->
            list.firstOrNull { it.id == orderId }
        }
    }

    override suspend fun getOrders(): Flow<List<Order>> {
        return _cache.asStateFlow()
    }

    private suspend fun removeDeliveredOrders() {
        val now = Instant.now()

        val result = _cache.value
            .filter {
                if (it.status != Status.Delivered) {
                    return@filter true
                }

                val deliveryDate = it.deliveredDate ?: return@filter false
                val duration = Duration.between(deliveryDate, now)

                duration.seconds < 15
            }

        _cache.emit(result)
    }

    private data class OrderImpl(
        override val id: Long,
        override val status: Status,
        override val openDate: Instant,
        override val deliveredDate: Instant?
    ) : Order
}