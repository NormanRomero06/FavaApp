package com.example.appfavas.modelos.Articulo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemInformeArticuloBinding

class ArticuloInformeAdapter(val articulosList: List<ArticuloInforme>) :
    RecyclerView.Adapter<ArticuloInformeAdapter.ArticuloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticuloViewHolder {
        val binding =
            ItemInformeArticuloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticuloViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) {
        val articuloInforme = articulosList[position]
        holder.bind(articuloInforme)
    }

    override fun getItemCount(): Int {
        return articulosList.size
    }

    inner class ArticuloViewHolder(private val binding: ItemInformeArticuloBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articulo: ArticuloInforme) {
            binding.tvIdProducto.text = articulo.idProducto.toString()
            binding.tvNombreArticulo.text = articulo.nombre
            binding.tvPrecioArticulo.text = articulo.precio.toString()
            binding.tvDescripcion.text = articulo.descripcion
            binding.tvExistencia.text = articulo.cantidad.toString()
            binding.tvCantidadMinima.text = articulo.cantidadMinima.toString()
            binding.tvCategoria.text = articulo.categoria


            // Configurar cualquier otra vista necesaria

            // Aquí también puedes implementar cualquier lógica adicional para manipular los elementos del ViewHolder

        }
    }
}
