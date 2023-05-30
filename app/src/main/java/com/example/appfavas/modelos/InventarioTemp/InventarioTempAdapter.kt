package com.example.appfavas.modelos.InventarioTemp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemCobroBinding

class InventarioTempAdapter : RecyclerView.Adapter<InventarioTempAdapter.InventarioTempViewHolder>() {

    private val data: MutableList<InventarioTemp> = mutableListOf()

    companion object {
        private const val MONTO_TOTAL_KEY = "montoTotal"
    }

    // Método para obtener los datos de la lista de productos
    fun getData(): List<InventarioTemp> {
        return data
    }

    var montoTotal: Float = 0.0f

    fun calcularMontoTotal(): Float {
        montoTotal = 0f
        for (cobro in data) {
            montoTotal += cobro.precioTotal
        }
        return montoTotal
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventarioTempViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCobroBinding.inflate(inflater, parent, false)
        return InventarioTempViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventarioTempViewHolder, position: Int) {
        val cobro = data[position]
        holder.bind(cobro)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<InventarioTemp>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class InventarioTempViewHolder(private val binding: ItemCobroBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cobro: InventarioTemp) {
            // Aquí puedes asignar los datos a las vistas en el item de RecyclerView
            binding.tvidProd.text = cobro.idProducto.toString()
            binding.tvPUnitario.text = cobro.precio.toString()
            binding.tvNombreProducto.text = cobro.nombre
            binding.tvCantProdTotal.text = cobro.cantidadVenta.toString()
            binding.tvPrecioTotal.text = cobro.precioTotal.toString()
            // ... asignar otros datos según tu diseño de item
        }
    }

}
