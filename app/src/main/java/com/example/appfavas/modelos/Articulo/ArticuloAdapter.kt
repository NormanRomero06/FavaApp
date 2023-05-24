package com.example.appfavas.modelos.Articulo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemTotalArticulosVentasBinding

class ArticuloAdapter(private val artList: ArrayList<Articulo>) :
    RecyclerView.Adapter<ArticuloAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemTotalArticulosVentasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun load(item: Articulo) {
            with(binding) {
                tvIdProducto.text = item.idProducto.toString()
                tvNombreArticulo.text = item.nombre
                tvDescripcion.text = item.descripcion
                tvPrecioArticulo.text = item.precio.toString()
                tvExistencia.text = item.cantidad.toString()
                //tvCategoria.text = item.Categoria_Nombre
                cwArticulo.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("idProducto", item.idProducto.toString())
                    bundle.putString("nombre", item.nombre)
                    bundle.putString("descripcion", item.descripcion)
                    bundle.putString("precio", item.precio.toString())
                    bundle.putString("cantidad", item.cantidad.toString())
                   //bundle.putString("Categoria_Nombre", item.Categoria_Nombre)
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.editar_EliminarArticulosVentasFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val artiItem = ItemTotalArticulosVentasBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(artiItem)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.artList[position])
    }
}