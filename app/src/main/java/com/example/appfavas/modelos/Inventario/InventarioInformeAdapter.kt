package com.example.appfavas.modelos.Inventario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemInformeInventarioBinding

class InventarioInformeAdapter(
         val infoInvList: List<InventarioInforme>):
        RecyclerView.Adapter<InventarioInformeAdapter.InventarioInformeViewHolder>() {

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): InventarioInformeAdapter.InventarioInformeViewHolder {
               val binding = ItemInformeInventarioBinding.inflate(
                       LayoutInflater.from(parent.context),
                       parent,
                       false
               )
                return InventarioInformeViewHolder(binding)
        }

        override fun getItemCount(): Int {
                return infoInvList.size
        }

        override fun onBindViewHolder(
                holder: InventarioInformeAdapter.InventarioInformeViewHolder,
                position: Int
        ) {
                val inventarioInforme = infoInvList[position]
                holder.bind(inventarioInforme)
        }

        inner class InventarioInformeViewHolder(private val binding: ItemInformeInventarioBinding):
                RecyclerView.ViewHolder(binding.root){

                fun bind(inventario: InventarioInforme){
                        binding.tvProdNombre.text = inventario.Producto
                        binding.tvMovimiento.text = inventario.Movimiento
                        binding.tvCantidad.text = inventario.cantidad.toString()
                        binding.tvFecha.text = inventario.fecha
                }

                }



}