package com.example.appfavas.modelos.Categoria

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemCategoriaVentaBinding


class CategoriaVentasAdapter(private val catVentaList: ArrayList<CategoriaVentas>): RecyclerView.Adapter<CategoriaVentasAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemCategoriaVentaBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: CategoriaVentas){
            with(binding){
                tvIdCat.text = item.id.toString()
                tvNombreCategoria.text = item.nombre
                cwCategoria.setOnClickListener {
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = ItemCategoriaVentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)

    }

    override fun getItemCount(): Int {
        return catVentaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.catVentaList[position])
    }
}


