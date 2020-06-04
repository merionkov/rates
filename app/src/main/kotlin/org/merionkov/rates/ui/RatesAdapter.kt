package org.merionkov.rates.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.merionkov.rates.data.CurrencyRateEntity
import org.merionkov.rates.ui.mvp.ListUpdate

class RatesAdapter(
    private val baseCurrencyCodeListener: (String) -> Unit,
    private val baseCurrencyRateListener: (Float) -> Unit
) : RecyclerView.Adapter<RateViewHolder>() {

    private var items: List<CurrencyRateEntity>? = null

    init {
        setHasStableIds(true)
    }

    fun submitItems(
        items: List<CurrencyRateEntity>,
        itemsUpdate: ListUpdate?
    ) {
        if (this.items == null || itemsUpdate == null) {
            this.items = items
            notifyDataSetChanged()
        } else {
            this.items = items
            applyItemsUpdate(itemsUpdate)
        }
    }

    private fun applyItemsUpdate(update: ListUpdate) {
        when (update) {
            is ListUpdate.ItemsUpdated -> {
                // For some (unknown) reasons RecyclerView recreates almost all view holders
                // after Adapter#notifyItemRangeChanged call (for wide ranges at least, i guess
                // this may be due to animations support). But nothing like this happens when
                // we call Adapter#notifyDataSetChanged instead.
                //
                // notifyItemRangeChanged(update.range.first, update.range.count())
                notifyDataSetChanged()
            }
            is ListUpdate.ItemMoved -> {
                notifyItemMoved(update.fromPosition, update.toPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(parent, baseCurrencyCodeListener, baseCurrencyRateListener)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(items!![position])
    }

    override fun getItemId(position: Int): Long {
        return items!![position].code.hashCode().toLong()
    }
}
