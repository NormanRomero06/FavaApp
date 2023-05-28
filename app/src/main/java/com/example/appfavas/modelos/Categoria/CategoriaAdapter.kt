package com.example.appfavas.modelos.Categoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemCategoriaBinding
import com.example.appfavas.modelos.Pago.Pago

class CategoriaAdapter(private var catList: ArrayList<Categoria>): RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemCategoriaBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: Categoria){
            with(binding){
                tvIdCat.text = item.id.toString()
                tvNombreCategoria.text = item.nombre
                cwCategoria.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.nav_articulos)
                }
                ivEditar.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("idCategoria", item.id.toString())
                    bundle.putString("nombre", item.nombre)
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.editarCategoriaFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)

    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.catList[position])
    }

    fun filter(text: String) {
        val searchText = text.toLowerCase()
        val filteredList = if (searchText.isEmpty()) {
            catList // Mostrar todos los elementos si no hay texto de búsqueda
        } else {
            val tempList = ArrayList<Categoria>()
            for (categoria in catList) {
                if (categoria.nombre.toLowerCase().contains(searchText)) {
                    tempList.add(categoria)
                }
            }
            tempList // Mostrar solo los elementos que coinciden con el texto de búsqueda
        }
        updateList(filteredList)
    }

    private fun updateList(list: List<Categoria>) {
        catList.clear()
        catList.addAll(list)
        notifyDataSetChanged()
    }
}