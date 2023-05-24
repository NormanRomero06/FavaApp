package com.example.appfavas.ui.Categoria


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
import com.example.appfavas.databinding.FragmentCrearCategoriaBinding

class CrearCategoriaFragment : Fragment() {

    private lateinit var binding: FragmentCrearCategoriaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCrearCategoriaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        crearCategoria()
        return root
    }

    fun crearCategoria() {
        binding.btnGuardarCategoria.setOnClickListener {
            try {
                val nombre = binding.etNombreCategoria.text.toString()
                val url = "http://localfavas.online/Categoria/InsertCategoria.php"
                val queue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        Toast.makeText(
                            context,
                            "Insertado existosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiar()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val parametros = HashMap<String, String>()
                        parametros.put("nombre", nombre)
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

    private fun limpiar() {
        with(binding) {
            //btnGuardarCategoria.setOnClickListener {
                etNombreCategoria.setText("")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

}
