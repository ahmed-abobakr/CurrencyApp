package com.ahmed.abobakr.currencyapp.details.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ahmed.abobakr.currencyapp.databinding.ItemHistoricalBinding
import com.ahmed.abobakr.currencyapp.details.data.ConvertBaseCurrency

class BaseCurrenciesConventionAdapter: ListAdapter<ConvertBaseCurrency, ViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil: DiffUtil.ItemCallback<ConvertBaseCurrency>(){
        override fun areItemsTheSame(
            oldItem: ConvertBaseCurrency,
            newItem: ConvertBaseCurrency
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ConvertBaseCurrency,
            newItem: ConvertBaseCurrency
        ) = oldItem.base == newItem.base
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return BaseCurrencyHolder(ItemHistoricalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as BaseCurrencyHolder).bind(getItem(position))
    }

    inner class BaseCurrencyHolder(private val binding: ItemHistoricalBinding): ViewHolder(binding.root){
        fun bind(item: ConvertBaseCurrency){
            with(binding){
                tvDay.text = item.base
                tvBase.text = "${item.value}"
            }
        }
    }
}