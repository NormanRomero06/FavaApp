package com.example.appfavas.modelos.Ventas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemInformeDetalleVentaBinding

class DetalleVentaInformeAdapter(val detalleventaList: List<DetalleVentaInforme>) :
    RecyclerView.Adapter<DetalleVentaInformeAdapter.DetalleVentaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleVentaViewHolder {
        val binding = ItemInformeDetalleVentaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetalleVentaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return detalleventaList.size
    }

    override fun onBindViewHolder(holder: DetalleVentaViewHolder, position: Int) {
        val detalleVentaInforme = detalleventaList[position]
        holder.bind(detalleVentaInforme)
    }


    inner class DetalleVentaViewHolder(private val binding: ItemInformeDetalleVentaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(detalleVenta: DetalleVentaInforme) {
            binding.tvNombreProducto.text = detalleVenta.Producto
            binding.tvCantidadVendida.text = detalleVenta.Cantidad_Vendida.toString()
            binding.tvPrecioUnitario.text = detalleVenta.Precio_Unitario.toString()
            binding.tvTotalDetalleVenta.text = detalleVenta.Total_Detalle_Venta.toString()
            binding.tvCodigoVenta.text = detalleVenta.Codigo_de_Venta.toString()
            binding.tvTotalVenta.text = detalleVenta.Total_Venta.toString()
            binding.tvUsuario.text = detalleVenta.Usuario
        }

    }


}