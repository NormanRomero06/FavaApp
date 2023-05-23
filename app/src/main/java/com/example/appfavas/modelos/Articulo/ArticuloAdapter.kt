package com.example.appfavas.modelos.Articulo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemCategoriaBinding
import com.example.appfavas.databinding.ItemProductoBinding
import com.example.appfavas.databinding.ItemTotalArticulosVentasBinding

class ArticuloAdapter(private val artList: ArrayList<Articulo>): RecyclerView.Adapter<ArticuloAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemTotalArticulosVentasBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: Articulo){
            with(binding){
                tvNombreArticulo.text = item.nombre
                tvExistencia.text = item.cantidad.toString()
                tvPrecioArticulo.text = item.precio.toString()
                cvArticulos.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.editar_EliminarArticulosVentasFragment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val artiItem = ItemTotalArticulosVentasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(artiItem)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.artList[position])
    }
}