package com.example.appfavas.modelos.Caja

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemCierreBinding
import com.example.appfavas.modelos.Ventas.VentasAdapter

class CierreAdapter(private val cierreList: ArrayList<Cierre>) :
    RecyclerView.Adapter<CierreAdapter.CierreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CierreViewHolder {
        val userItem =
            ItemCierreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CierreViewHolder(userItem)

    }

    override fun getItemCount(): Int {
        return cierreList.size
    }

    override fun onBindViewHolder(holder: CierreViewHolder, position: Int) {
        holder.load(this.cierreList[position])
    }

    class CierreViewHolder(private val binding: ItemCierreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun load(cierre: Cierre) {
            // Aquí establece los valores de los elementos de la vista con los datos de la venta
            binding.tvFecha.text = cierre.fechaHora
            binding.tvTotalCaja.text = cierre.resultado
        }
    }
}
