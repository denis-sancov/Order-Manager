package com.sancov.order.list

import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import com.sancov.control_kit.rv.AnyAdapter
import com.sancov.control_kit.rv.ViewHolder
import com.sancov.order.model_ex.titleResId
import com.sancov.orders.R

internal class OrderListAdapter : AnyAdapter<OrderListItem>(OrderListItem.DIFF) {
    init {
        setHasStableIds(true)
        bind(R.layout.order_list_rv_item, ::ItemViewHolder)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(model: OrderListItem): Int {
        return R.layout.order_list_rv_item
    }
}

private class ItemViewHolder(itemView: View) : ViewHolder<OrderListItem>(itemView) {
    private val txtTitle: AppCompatTextView = itemView.findViewById(R.id.txt_title)
    private val btnStatus: Button = itemView.findViewById(R.id.btn_status)

    override fun bind(item: OrderListItem) {
        txtTitle.text = itemView.resources.getString(R.string.order_list_order_number, item.id)

        btnStatus.setText(item.status.titleResId)

        btnStatus.setOnClickListener {
            item.actionFlow.tryEmit(
                Action.UpdateStatus(
                    item.id,
                    item.status.resolveNextStatus()
                )
            )
        }

        itemView.setOnClickListener {
            item.actionFlow.tryEmit(
                Action.OpenDetails(
                    item.id
                )
            )
        }
    }
}