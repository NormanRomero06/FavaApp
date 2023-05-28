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
import com.example.appfavas.databinding.FragmentPagosBinding

class PagosFragment : Fragment() {

    private lateinit var binding: FragmentPagosBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentPagosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        realizarPago()

        return root
    }


    fun realizarPago() {

        binding.btnRealizarPago.setOnClickListener {
            try {
                if (validarCampos()) {
                    var descripcion = binding.etDescripcion.text.toString()
                    var cantidad = binding.etCantidad.text.toString()
                    val url = "http://localfavas.online/Egresos/InsertEgresos.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(Request.Method.POST,
                        url,
                        Response.Listener<String> { response ->
                            Toast.makeText(activity, "Pago realizado", Toast.LENGTH_LONG).show()
                        },
                        Response.ErrorListener { error ->
                            Toast.makeText(
                                activity,
                                "Error $error",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiar()

                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("descripcion", descripcion)
                            parametros.put("monto", cantidad)
                            return parametros

                        }
                    }
                    queue.add(resultadoPost)
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error al insertar: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
            // val navController = Navigation.findNavController(binding.root)
            //navController.navigate(R.id.listaPagosFragment)


        }


    }

    private fun limpiar() {
        with(binding) {
            etDescripcion.setText("")
            etCantidad.setText("")
        }
    }

    private fun validarCampos(): Boolean {
        var valido = true
        var descripcion = binding.etDescripcion.text.toString()
        var cantidad = binding.etCantidad.text.toString()

        if (descripcion.isNullOrEmpty()) {
            binding.etDescripcion.setError("Por favor rellene este campo")
            valido = false
        }
        if (cantidad.isNullOrEmpty()) {
            binding.etCantidad.setError("Por favor rellene este campo")
            valido = false
        }
        return valido
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}