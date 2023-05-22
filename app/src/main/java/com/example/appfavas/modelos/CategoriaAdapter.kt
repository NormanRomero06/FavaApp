package com.example.appfavas.modelos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemCategoriaBinding

class CategoriaAdapter(private val catList: ArrayList<Categoria>): RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategoriaBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: Categoria){
            with(binding){
                tvIdCat.text = item.id
                tvNombreCategoria.text = item.nombre
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