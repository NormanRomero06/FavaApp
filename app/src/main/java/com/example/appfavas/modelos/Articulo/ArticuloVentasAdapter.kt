package com.example.appfavas.modelos.Articulo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemProductoBinding


class ArticuloVentasAdapter(private val ListArticulosVenta: ArrayList<ArticuloVentas>): RecyclerView.Adapter<ArticuloVentasAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemProductoBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: ArticuloVentas){
            with(binding){


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)

    }

    override fun getItemCount(): Int {
        return ListArticulosVenta.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.ListArticulosVenta[position])
    }
}


