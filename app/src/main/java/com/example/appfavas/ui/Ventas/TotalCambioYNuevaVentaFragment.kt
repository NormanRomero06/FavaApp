package com.example.appfavas.ui.Ventas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.dao.ShareObjectAdp
import com.example.appfavas.databinding.FragmentTotalCambioYNuevaVentaBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTempAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TotalCambioYNuevaVentaFragment : Fragment() {
    private var _binding: FragmentTotalCambioYNuevaVentaBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase
    private lateinit var adapter: InventarioTempAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalCambioYNuevaVentaBinding.inflate(inflater, container, false)
        val rootView = binding.root
        setupTextView()
        botonazo()

        return rootView
    }

    private fun setupTextView() {
        val montoTotal = getMontoTotalFromSharedPreferences()
        binding.TvTotal.text = String.format("%.2f", montoTotal)
        val cambio = obtenerCambioSharedPreferences()
        val vuelto = montoTotal - (cambio?.toFloat() ?: 0f)
        val vueltoAbsoluto = Math.abs(vuelto)
        binding.tvCambio.text = String.format("%.2f", vueltoAbsoluto)
    }
    private fun getMontoTotalFromSharedPreferences(): Float {
        val sharedPreferences =
            requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("montoTotal", 0f)
    }

    private fun obtenerCambioSharedPreferences(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getString("cambio", "")
    }
    private fun botonazo(){
        binding.fabNuevaVenta.setOnClickListener {
        Navigation.findNavController(binding.root).navigate(R.id.nav_ventas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}