package com.example.appfavas.ui.cierreCaja


import android.os.Bundle
import android.util.Log
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
import com.example.appfavas.databinding.FragmentCierreCajaBinding
import org.json.JSONException
import org.json.JSONObject


class CierreCaja : Fragment() {
    private var _binding: FragmentCierreCajaBinding? = null
    private val binding get() = _binding!!
    private var ingresos = 0f
    private var pagos = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCierreCajaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        obtenerEgresos()
        obtenerIngresos()
        insertarCierre()
        return root


    }

    private fun obtenerIngresos() {
        val url = "http://localfavas.online/Caja/ObtenerCantidad.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val montoTotal = response.toFloat()
                    ingresos = montoTotal
                    println("Monto Total (Ingresos): $ingresos")
                    binding.TvTotal.text = ingresos.toString()
                } catch (e: JSONException) {
                    println("Error parsing JSON: ${e.message}")
                }
            },
            { error ->
                // Handle error
                println("Error en la solicitud: ${error.message}")
            }
        )

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(stringRequest)
    }

    private fun obtenerEgresos() {
        val url = "http://localfavas.online/Egresos/ObtenerDatos.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val montoTotal = response.toFloat()
                    pagos = montoTotal
                    println("Monto Total (Pagos): $pagos")
                    binding.tvCambio.text = pagos.toString()
                } catch (e: JSONException) {
                    println("Error parsing JSON: ${e.message}")
                }
            },
            { error ->
                // Handle error
                println("Error en la solicitud: ${error.message}")
            }
        )

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(stringRequest)
    }
    private fun insertarCierre() {
        binding.btnGenerar.setOnClickListener {


            val url = "http://localfavas.online/CierreCaja/InsertCierreCaja.php"
            val queue = Volley.newRequestQueue(requireActivity())
            val resultadoPost = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(
                        requireContext(),
                        "Cierre elaborado",
                        Toast.LENGTH_LONG
                    ).show()
                    limpiar()
                    Navigation.findNavController(binding.root).navigate(R.id.nav_ventas)
                }, Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val parametros = HashMap<String, String>()
                    parametros["totalEfectivoCaja"] = binding.EtDineroCaja.text.toString()
                    parametros["totalEgreso"] = pagos.toString()
                    parametros["totalDineroVentas"] = ingresos.toString()
                    Log.d("Prueba1", "Parametros:$parametros") // Prueba
                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    }

    fun limpiar(){
        binding.EtDineroCaja.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
