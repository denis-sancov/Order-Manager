package com.sancov.order.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sancov.control_kit.ex.consumeWindowInsets
import com.sancov.control_kit.fragment.ActionBarFragment
import com.sancov.orders.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal object BundleKey {
    const val ORDER_ID = "ORDER_ID"
}

@AndroidEntryPoint
internal class OrderDetailsFragment : ActionBarFragment(R.layout.order_details_fragment) {
    private val vm: OrderDetailsViewModel by viewModels()

    private lateinit var txtTitle: AppCompatTextView
    private lateinit var txtDate: AppCompatTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtTitle = view.findViewById(R.id.txt_title)
        txtDate = view.findViewById(R.id.txt_date)

        consumeWindowInsets { v, insets ->
            toolbar.updatePadding(top = insets.top)
            v.updatePadding(bottom = insets.bottom)

            WindowInsetsCompat.CONSUMED
        }

        bindFlows()
    }

    private fun bindFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { bindOrderDetailsFlow() }
            }
        }
    }

    private suspend fun bindOrderDetailsFlow() {
        val df = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withZone(ZoneId.systemDefault())

        vm.orderFlow.mapNotNull { it }.collect {
            txtTitle.text = resources.getString(R.string.order_list_order_number, it.id)
            txtDate.text = df.format(it.openDate)
        }
    }

    companion object {
        fun instantiate(orderId: Long): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()

            fragment.arguments = bundleOf(
                BundleKey.ORDER_ID to orderId
            )

            return fragment
        }
    }
}