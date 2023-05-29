package com.example.appfavas.modelos.Articulo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemProductoBinding


class ArticuloVentasAdapter(private val ListArticulosVenta: ArrayList<ArticuloVentas>) :
    RecyclerView.Adapter<ArticuloVentasAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var cantidadTotal = 0

        init {
            binding.IvMas.setOnClickListener {
                incrementarCantidad()
            }

            binding.IvMenos.setOnClickListener {
                decrementarCantidad()
            }
        }

        private fun incrementarCantidad() {
            cantidadTotal++
            binding.tvCantProd.text = cantidadTotal.toString()
        }

        private fun decrementarCantidad() {
            if (cantidadTotal > 0) {
                cantidadTotal--
                binding.tvCantProd.text = cantidadTotal.toString()
            }
        }

        fun load(item: ArticuloVentas) {
            with(binding) {
                tvNombreProducto.text = item.nombre
                PrecioTotal.text = item.precio.toString()
                tvCantProd.text = cantidadTotal.toString()
            }

            binding.cwPrducto.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("idProducto", item.idProducto.toString())
                bundle.putString("nombre", item.nombre)
                bundle.putString("precio", item.precio.toString())
                bundle.putInt("cantidad", cantidadTotal)

                val precioTotal = item.precio * cantidadTotal
                bundle.putString("precioTotal", precioTotal.toString())

                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.checkProductoFragment, bundle)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem =
            ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)
    }

    override fun getItemCount(): Int {
        return ListArticulosVenta.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.ListArticulosVenta[position])
    }
}




