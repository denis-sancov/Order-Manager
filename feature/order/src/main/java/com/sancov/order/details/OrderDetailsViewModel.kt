package com.sancov.order.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sancov.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class OrderDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: OrderRepository
) : ViewModel() {
    private val _orderId by lazy {
        savedStateHandle.get<Long>(BundleKey.ORDER_ID) as Long
    }

    val orderFlow = repository
        .getOrder(_orderId)
        .mapNotNull { it.data }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}