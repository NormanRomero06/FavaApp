package com.example.appfavas.modelos.Articulo

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemTotalArticulosVentasBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp

class ArticuloAdapter(private val artList: ArrayList<Articulo>) :
    RecyclerView.Adapter<ArticuloAdapter.ViewHolder>() {

    private val data: MutableList<Articulo> = mutableListOf()

    class ViewHolder(private val binding: ItemTotalArticulosVentasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun load(item: Articulo) {
            with(binding) {
                tvIdProducto.text = item.idProducto.toString()
                tvNombreArticulo.text = item.nombre
                tvDescripcion.text = item.descripcion
                tvPrecioArticulo.text = item.precio.toString()
                tvExistencia.text = item.cantidad.toString()
                tvCategoria.text = item.Categoria_idCategoria.toString()
                cwArticulo.setOnClickListener {
                    val navController = Navigation.findNavController(binding.root)
                    val currentDestination = navController.currentDestination

                    if (currentDestination?.id == R.id.nav_articulos) {
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

                    if(currentDestination?.id == R.id.nav_inventario){
                        val bundle = Bundle()
                        bundle.putString("idProducto", item.idProducto.toString())
                        bundle.putString("nombre", item.nombre)
                        bundle.putString("descripcion", item.descripcion)
                        bundle.putString("precio", item.precio.toString())
                        bundle.putString("cantidad", item.cantidad.toString())
                        bundle.putString("Categoria_Nombre", item.Categoria_idCategoria.toString())
                        Log.d(TAG, "Parametros:$bundle")
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.agregarInventarioFragment, bundle)
                    }
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

    fun getData(): MutableList<Articulo> {
        return data
    }


    override fun getItemCount(): Int {
        return artList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(artList[position])
    }

    fun filter(text: String) {
        val searchText = text.toLowerCase()
        val filteredList = if (searchText.isEmpty()) {
            artList // Mostrar todos los elementos si no hay texto de búsqueda
        } else {
            val tempList = ArrayList<Articulo>()
            for (articulo in artList) {
                if (articulo.nombre.toLowerCase().contains(searchText)) {
                    tempList.add(articulo)
                }
            }
            tempList // Mostrar solo los elementos que coinciden con el texto de búsqueda
        }
        updateList(filteredList)
    }

    private fun updateList(list: List<Articulo>) {
        artList.clear()
        artList.addAll(list)
        notifyDataSetChanged()
    }

}
