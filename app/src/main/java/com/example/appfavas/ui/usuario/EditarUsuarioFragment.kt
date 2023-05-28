package com.example.appfavas.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentEditarUsuarioBinding
import com.example.appfavas.modelos.Categoria.Categoria
import org.json.JSONException


class EditarUsuarioFragment : Fragment() {
    private lateinit var binding: FragmentEditarUsuarioBinding
    private lateinit var categorias: MutableList<Categoria>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditarUsuarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        categorias = mutableListOf()
        editarUsuario()
        //btnLimpiar()
        return root
    }

    fun editarUsuario() {
        val idUsuario = arguments?.getString("idUsuario")
        val nombres = arguments?.getString("nombres")
        val apellidos = arguments?.getString("apellidos")
        val correo = arguments?.getString("correo")
        val usuario = arguments?.getString("usuario")
        val contraseña = arguments?.getString("contraseña")

        binding.etId.setText(idUsuario)
        binding.etNombres.setText(nombres)
        binding.etApellidos.setText(apellidos)
        binding.etCorreo.setText(correo)
        binding.etUsuario.setText(usuario)
        binding.etContraseA.setText(contraseña)

        with(binding) {
            btnEditar.setOnClickListener {
                try {

                    val url = "http://localfavas.online/Usuario/UpdateUsuario.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Modificado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiarCampos()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("idUsuario", binding.etId.text.toString())
                            parametros.put("nombres", binding.etNombres.text.toString())
                            parametros.put("apellidos", binding.etApellidos.text.toString())
                            parametros.put("correo", binding.etCorreo.text.toString())
                            parametros.put("usuario", binding.etUsuario.text.toString())
                            parametros.put("contraseña", binding.etContraseA.toString())
                            parametros.put("rol", binding.sRoles.selectedItem.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)

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

    /*fun btnLimpiar() {
        binding.btnLimpiarArt.setOnClickListener {
            limpiarCampos()
        }
    }*/

    private fun limpiarCampos() {
        with(binding) {
            etNombres.text?.clear()
            etApellidos.text?.clear()
            etCorreo.text?.clear()
            etUsuario.text?.clear()
            etContraseA.text?.clear()
            sRoles.setSelection(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}