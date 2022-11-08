package com.sancov.order.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sancov.domain.model.EmptyState
import com.sancov.domain.model.Status
import com.sancov.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class OrderListViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {
    private val _actionFlow = MutableSharedFlow<Action>(
        replay = 0,
        extraBufferCapacity = 1
    )

    val itemsFlow = repository
        .getOrders()
        .map { it.data ?: emptyList() }
        .map { list ->
            list.map {
                OrderListItem(it.id, it.status, _actionFlow)
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun getActionFlow(): Flow<Action> {
        return _actionFlow
    }

    fun createOrder(): Flow<EmptyState> {
        return repository.createOrder()
    }

    fun updateOrderStatus(orderId: Long, status: Status): Flow<EmptyState> {
        return repository.updateOrderStatus(orderId, status)
    }
}