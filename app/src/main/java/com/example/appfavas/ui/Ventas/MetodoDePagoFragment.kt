package com.example.appfavas.ui.Ventas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentMetodoDePagoBinding

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

        return binding.root
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
                //val cantidad = binding.TvTotal
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
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            val idVenta = obtenerIdUsuarioDesdeSharedPreferences()
                            parametros.put("tipoTransaccion", "Efectivo")
                            parametros.put("montoTransaccion", recibido)
                            parametros.put("Venta_idVenta", "23")
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

    private fun obtenerIdUsuarioDesdeSharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idUsuario", 0)
    }


    fun navigation() {
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
