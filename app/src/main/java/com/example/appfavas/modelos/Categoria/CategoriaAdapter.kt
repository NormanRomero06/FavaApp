package com.example.appfavas.modelos.Categoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemCategoriaBinding

class CategoriaAdapter(private val catList: ArrayList<Categoria>): RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

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
}