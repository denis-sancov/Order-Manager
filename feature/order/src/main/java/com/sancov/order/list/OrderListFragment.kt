package com.sancov.order.list

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sancov.control_kit.ex.consumeWindowInsets
import com.sancov.control_kit.ex.requireRouterFragmentManager
import com.sancov.control_kit.fragment.ActionBarFragment
import com.sancov.control_kit.rv.decoration.MarginDecoration
import com.sancov.control_kit.rv.decoration.SpacingDecoration
import com.sancov.domain.model.Status
import com.sancov.orders.R
import com.sancov.routers.OrderRouter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class OrderListFragment : ActionBarFragment(R.layout.order_list_fragment) {
    private val vm: OrderListViewModel by viewModels()

    private lateinit var btnPlaceOrder: Button
    private lateinit var rv: RecyclerView

    @Inject
    internal lateinit var router: OrderRouter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPlaceOrder = view.findViewById(R.id.btn_place_order)
        btnPlaceOrder.setOnClickListener {
            createOrder()
        }

        rv = view.findViewById(R.id.recycler_view)

        rv.also {
            it.layoutManager = LinearLayoutManager(view.context)

            it.addItemDecoration(
                SpacingDecoration(
                    ctx = it.context,
                    spacingRes = com.sancov.ui.design_system.R.dimen.spacing_sm
                )
            )

            it.addItemDecoration(
                MarginDecoration(
                    ctx = it.context,
                    horizontalResId = com.sancov.ui.design_system.R.dimen.spacing_md
                )
            )

            it.adapter = OrderListAdapter()
        }

        consumeWindowInsets { _, insets ->
            toolbar.updatePadding(top = insets.top)
            rv.updatePadding(bottom = insets.bottom)

            WindowInsetsCompat.CONSUMED
        }

        bindFlows()
    }

    private fun bindFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { bindItemsFlow() }
                launch { bindActionFlow() }
            }
        }
    }

    private suspend fun bindItemsFlow() {
        val adapter = rv.adapter as OrderListAdapter

        vm.itemsFlow.collect {
            adapter.submit(it) {
                rv.invalidateItemDecorations()
            }
        }
    }

    private suspend fun bindActionFlow() {
        vm.getActionFlow().collect {
            when (it) {
                is Action.OpenDetails -> router.toOrder(requireRouterFragmentManager(), it.orderId)
                is Action.UpdateStatus -> updateStatus(it.orderId, it.status)
            }
        }
    }

    private fun createOrder() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.createOrder().collect()
        }
    }

    private fun updateStatus(orderId: Long, status: Status) {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.updateOrderStatus(orderId, status).collect()
        }
    }
}