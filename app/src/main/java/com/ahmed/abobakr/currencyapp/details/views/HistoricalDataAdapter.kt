package com.ahmed.abobakr.currencyapp.details.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ahmed.abobakr.currencyapp.R
import com.ahmed.abobakr.currencyapp.databinding.ItemHistoricalBinding

class HistoricalDataAdapter: ListAdapter<Double, ViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil: DiffUtil.ItemCallback<Double>(){
        override fun areItemsTheSame(oldItem: Double, newItem: Double) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Double, newItem: Double) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return HistoricalDataItem(ItemHistoricalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as HistoricalDataItem).bind(getItem(position), position)
    }

    inner class HistoricalDataItem(private val binding: ItemHistoricalBinding): ViewHolder(binding.root){
        fun bind(item: Double, position: Int){
            with(binding){
                tvDay.text = "${binding.root.context.getString(R.string.day)}${position + 1}"
                tvBase.text = "$item"
            }
        }
    }
}