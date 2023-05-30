package com.example.appfavas.ui.Ventas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentMetodoDePagoBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTempAdapter

class MetodoDePagoFragment : Fragment() {
    private lateinit var binding: FragmentMetodoDePagoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMetodoDePagoBinding.inflate(inflater, container, false)
        setupTextView()
        navigation()
        return binding.root
    }

    private fun setupTextView() {
        val montoTotal = getMontoTotalFromSharedPreferences()
        binding.TvTotal.text = String.format("%.2f", montoTotal)
    }

    private fun getMontoTotalFromSharedPreferences(): Float {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("montoTotal", 0f)
    }


    fun navigation()
    {
        binding.btnTarjeta.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.totalTarjetaFragment)
        }
        binding.btnEfectivo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.nav_ventas)
            Toast.makeText(context, "Pago en efectivo realizado", Toast.LENGTH_SHORT).show()
        }
        binding.btnDLares.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.metodoDePagoFragment)
        }
        binding.btnDividir.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.dividirPagoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}