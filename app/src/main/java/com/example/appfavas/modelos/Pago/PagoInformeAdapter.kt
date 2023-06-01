package com.example.appfavas.modelos.Pago

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemListaPagosBinding

class PagoInformeAdapter(val pagoList: List<Pago>) :
    RecyclerView.Adapter<PagoInformeAdapter.PagoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagoViewHolder {
        val binding = ItemListaPagosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagoViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return pagoList.size
    }

    override fun onBindViewHolder(holder: PagoViewHolder, position: Int) {
        val pagoInforme = pagoList[position]
        holder.bind(pagoInforme)
    }


    inner class PagoViewHolder(private val binding: ItemListaPagosBinding):
    RecyclerView.ViewHolder(binding.root) {

        fun bind(pago: Pago){
            binding.idPago.text = pago.idPagos.toString()
            binding.tvDescripcion.text = pago.descripcion
            binding.tvMonto0.text = pago.monto.toString()
            binding.tvFecha.text = pago.fechaPago

    }

}

}