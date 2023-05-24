package com.example.appfavas.modelos.Pago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemListaPagosBinding

class PagoAdapter(private val pagoList: ArrayList<Pago>): RecyclerView.Adapter<PagoAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemListaPagosBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: Pago){
            with(binding){
                idPago.text = item.idPagos.toString()
                tvDescripcion.text = item.descripcion
                tvMonto0.text = item.monto.toString()
                tvFecha.text = item.fechaPago
                cwPagos.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("idPagos", item.idPagos.toString())
                    bundle.putString("descripcion", item.descripcion)
                    bundle.putString("monto", item.monto.toString())
                    bundle.putString("fechaHora", item.fechaPago)
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.nav_pagos)

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pagoItem = ItemListaPagosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(pagoItem)
    }

    override fun getItemCount(): Int {
        return pagoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.pagoList[position])
    }

}