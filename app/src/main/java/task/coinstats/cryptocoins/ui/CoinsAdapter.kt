package task.coinstats.cryptocoins.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import task.coinstats.cryptocoins.R
import task.coinstats.cryptocoins.data.models.local.Coin
import task.coinstats.cryptocoins.databinding.ItemCoinBinding
import task.coinstats.cryptocoins.utils.toFormattedAmountWithCurrency
import task.coinstats.cryptocoins.utils.toFormattedDeltaPercent

class CoinsAdapter : ListAdapter<Coin, CoinsAdapter.CoinViewHolder>(DIFF_UTIL) {

    inner class CoinViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.run {
                name.text = coin.name
                image.load(coin.iconUrl)
                rank.text = coin.rank.toString()
                deltaPercent.text = coin.delta24.toFormattedDeltaPercent()
                price.text = coin.priceInUSD.toFormattedAmountWithCurrency(coin.valueCurrency)
                currencySymbol.text = coin.symbol

                if (coin.delta24 < 0) {
                    deltaPercent.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    deltaPercent.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red_alpha))
                    deltaPercent.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_arrow_down, 0,0,0)
                } else {
                    deltaPercent.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    deltaPercent.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green_alpha))
                    deltaPercent.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_arrow_up, 0,0,0)
                }
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
        CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}