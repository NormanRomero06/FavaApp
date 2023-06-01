package com.example.appfavas.ui.Ventas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.dao.ShareObjectAdp
import com.example.appfavas.databinding.FragmentMetodoDePagoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        efectivoInsertar()
        tarjetaInsertar()


        return binding.root
    }

    private fun tarjetaInsertar() {
        with(binding) {
            btnTarjeta.setOnClickListener {
                try {
                    val recibido = EtEfectivoR.text.toString()
                    val url = "http://localfavas.online/Caja/InsertCaja.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Insertado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            Navigation.findNavController(binding.root).navigate(R.id.nav_TotalCambioNueva)
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            val idVenta = obtenerIdVentaDesdeSharedPreferences()
                            parametros.put("tipoTransaccion", "Tarjeta")
                            parametros.put("montoTransaccion", recibido)
                            parametros.put("Venta_idVenta", idVenta.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)

                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al insertar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun setupTextView() {
        val montoTotal = getMontoTotalFromSharedPreferences()
        binding.TvTotal.text = String.format("%.2f", montoTotal)
    }

    private fun getMontoTotalFromSharedPreferences(): Float {
        val sharedPreferences =
            requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("montoTotal", 0f)
    }

    fun efectivoInsertar() {
        with(binding) {
            btnEfectivo.setOnClickListener {

                try {
                    val recibido = EtEfectivoR.text.toString()
                    validacion()
                    val url = "http://localfavas.online/Caja/InsertCaja.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Insertado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            Navigation.findNavController(binding.root).navigate(R.id.nav_TotalCambioNueva)
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            val idVenta = obtenerIdVentaDesdeSharedPreferences()
                            parametros.put("tipoTransaccion", "Efectivo")
                            parametros.put("montoTransaccion", recibido)
                            parametros.put("Venta_idVenta", idVenta.toString())
                            getCambio(recibido)
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)

                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al insertar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun obtenerIdVentaDesdeSharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idVenta", 0)
    }
    private fun getCambio(Dinero: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("cambio", Dinero)
        editor.apply()
    }

    fun validacion(){
       val Efectivo = binding.EtEfectivoR.text.toString()
        if (Efectivo.isEmpty()){
            Toast.makeText(
                requireContext(),
                "Por favor Digite el dinero recibido",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun navigation() {
        binding.btnTarjeta.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.totalTarjetaFragment)
        }
        binding.btnEfectivo.setOnClickListener {
            Toast.makeText(context, "Pago en efectivo realizado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
