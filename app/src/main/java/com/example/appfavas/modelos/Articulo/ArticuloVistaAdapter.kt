package com.example.appfavas.modelos.Articulo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemTotalArticulosVentasBinding

class ArticuloVistaAdapter(private val artVList: ArrayList<ArticuloVista>): RecyclerView.Adapter<ArticuloVistaAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemTotalArticulosVentasBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: ArticuloVista){
            with(binding){
                tvNombreArticulo.text = item.nombre
                tvExistencia.text = item.cantidad.toString()
                tvPrecioArticulo.text = item.precio.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val artiVItem = ItemTotalArticulosVentasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(artiVItem)
    }

    override fun getItemCount(): Int {
        return artVList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.artVList[position])
    }
}