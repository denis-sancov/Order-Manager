package com.sancov.order.list

import androidx.recyclerview.widget.DiffUtil
import com.sancov.domain.model.Status
import kotlinx.coroutines.flow.MutableSharedFlow

internal sealed class Action {
    data class OpenDetails(val orderId: Long) : Action()
    data class UpdateStatus(val orderId: Long, val status: Status) : Action()
}

internal data class OrderListItem(
    val id: Long,
    val status: Status,
    val actionFlow: MutableSharedFlow<Action>
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<OrderListItem>() {
            override fun areItemsTheSame(oldItem: OrderListItem, newItem: OrderListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OrderListItem,
                newItem: OrderListItem
            ): Boolean {
                return oldItem.status == newItem.status
            }
        }
    }
}