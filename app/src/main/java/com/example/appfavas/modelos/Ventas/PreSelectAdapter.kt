package com.example.appfavas.modelos.Ventas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemProductoBinding
import com.example.appfavas.modelos.Articulo.ArticuloVentas

class PreSelectAdapter(private val LisPreVenta: ArrayList<PreSelect>): RecyclerView.Adapter<PreSelectAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemProductoBinding ): RecyclerView.ViewHolder(binding.root){
        fun load(item: PreSelect) {
            with(binding) {
                tvNombreProducto.text = item.nombre
                PrecioTotal.text = item.precio.toString()
                //Crear item luego
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)

    }

    override fun getItemCount(): Int {
        return LisPreVenta.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.LisPreVenta[position])
    }
}


