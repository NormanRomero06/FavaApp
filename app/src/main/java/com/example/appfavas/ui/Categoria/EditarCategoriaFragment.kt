package com.example.appfavas.ui.Categoria

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentEditarCategoriaBinding

class EditarCategoriaFragment : Fragment() {
    private var _binding: FragmentEditarCategoriaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarCategoriaBinding.inflate(inflater, container, false)
        val rootView = binding.root


        // Utiliza los datos según sea necesario

        editarCategoria()
        btnLimpiar()
        eliminarCategoria()
        return rootView
    }


    fun editarCategoria() {
        val idCategoria = arguments?.getString("idCategoria")
        val nombre = arguments?.getString("nombre")
        binding.etId.setText(idCategoria)
        binding.etNombreCategoria.setText(nombre)

        with(binding) {
            btnEditarCategoria.setOnClickListener {
                try {
                    if (validarCampos()) {
                        val url = "http://localfavas.online/Categoria/UpdateCategoria.php"
                        val queue = Volley.newRequestQueue(activity)
                        val resultadoPost = object : StringRequest(
                            Request.Method.POST, url,
                            Response.Listener<String> { response ->
                                Toast.makeText(
                                    context,
                                    "Modificado existosamente",
                                    Toast.LENGTH_LONG
                                ).show()
                                limpiar()
                            }, Response.ErrorListener { error ->
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                            }) {
                            override fun getParams(): MutableMap<String, String>? {
                                val parametros = HashMap<String, String>()
                                parametros.put("idCategoria", binding.etId.text.toString())
                                parametros.put("nombre", binding.etNombreCategoria.text.toString())
                                parametros.put("imagen", "")
                                Log.d("Prueba4", "Parametros:$parametros")//Prueba
                                return parametros
                            }
                        }

                        queue.add(resultadoPost)
                    }
                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al editar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    fun btnLimpiar() {
        binding.btnLimpiarCampos.setOnClickListener {
            limpiar()
        }
    }

    fun limpiar() {
        with(binding) {
            etId.setText("")
            etNombreCategoria.setText("")
        }
    }

    private fun validarCampos(): Boolean {
        var valido = true
        val nombre = binding.etNombreCategoria.text.toString()

        if (nombre.isNullOrEmpty()) {
            binding.etNombreCategoria.setError("Por favor rellene este campo")
            valido = false
        }
        return valido
    }

    fun eliminarCategoria() {
        with(binding) {
            val idCategoria = arguments?.getString("idCategoria")
            binding.etId.setText(idCategoria)
            btnEliminarCategoria.setOnClickListener {
                try {
                    val url = "http://localfavas.online/Categoria/DeleteCategoria.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Eliminado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiar()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val parametros = HashMap<String, String>()
                            parametros.put("idCategoria", binding.etId.text.toString())
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


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}