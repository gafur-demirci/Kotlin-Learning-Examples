package com.abdulgafur.demirci.cryptocurrencies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.abdulgafur.demirci.cryptocurrencies.databinding.RecyclerRowBinding
import com.abdulgafur.demirci.cryptocurrencies.model.Currency

class RecyclerAdapter(private val cryptos: ArrayList<Currency>, private  val listener : Listener): Adapter<RecyclerAdapter.RowHolder>() {

    interface  Listener {
        fun onItemClick(currency: Currency)
    }

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93", "#0d9de3", "#ffe48f")

    class RowHolder(private val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(currency)
            }
            itemView.setBackgroundColor(colors[position % 8].toColorInt())
            binding.cryptoName.text = currency.currency
            binding.cryptoPrice.text = currency.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptos[position], colors, position, listener)

    }

    override fun getItemCount(): Int {
        return cryptos.size
    }

}