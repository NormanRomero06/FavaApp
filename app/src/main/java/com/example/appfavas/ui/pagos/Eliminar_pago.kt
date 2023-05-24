package com.example.appfavas.ui.pagos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentEliminarPagoBinding


class Eliminar_pago : Fragment() {
    private var _binding: FragmentEliminarPagoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarPagoBinding.inflate(inflater, container, false)
        val rootView = binding.root
        eliminarPago()
        cargarDatos()

        return rootView
    }

    fun cargarDatos() {
        val idPagos = arguments?.getString("idPagos")
        val descripcion = arguments?.getString("descripcion")
        val monto = arguments?.getString("monto")
        val fechaPago = arguments?.getString("fechaPago")


        with(binding) {
            tvIdPago.setText(idPagos)
            etDescripcion.setText(descripcion)
            etCantidad.setText(monto)
            etFechaPago.setText(fechaPago)
        }
    }


    fun eliminarPago() {
        binding.btnEliminarPago.setOnClickListener {
            try {
                val url = "http://localfavas.online/Egresos/DeleteEgresos.php"
                val queue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        Toast.makeText(
                            context,
                            "Eliminado existosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiarCampos()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String, String>()
                        parametros.put("idPagos", binding.tvIdPago.text.toString())
                        return parametros
                    }
                }
                queue.add(resultadoPost)


            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error al Eliminar: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }

    fun limpiarCampos() {
        with(binding) {
            tvIdPago.setText("")
            etDescripcion.setText("")
            etCantidad.setText("")
            etFechaPago.setText("")


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
