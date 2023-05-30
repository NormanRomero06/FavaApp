package com.example.appfavas.ui.usuario

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
import com.example.appfavas.databinding.FragmentRecuperarPWBinding

class RecuperarPWFragment : Fragment() {

    private lateinit var binding: FragmentRecuperarPWBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecuperarPWBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recuperarPW()
        return root
    }

    private fun recuperarPW() {
        val nombres = arguments?.getString("nombres")
        val apellidos = arguments?.getString("apellidos")
        val correo = arguments?.getString("correo")
        val nuevaContraseña = arguments?.getString("contraseña")

        binding.etNombres.setText(nombres)
        binding.etApellidos.setText(apellidos)
        binding.etCorreo.setText(correo)
        binding.etNuevaContraseA.setText(nuevaContraseña)

        with(binding) {
            btnCambiarPW.setOnClickListener {
                try {
                    if (validarCampos()) {
                        val url = "http://localfavas.online/Usuario/UpdatePWUser.php"
                        val queue = Volley.newRequestQueue(activity)
                        val resultadoPost = object : StringRequest(
                            Method.POST, url,
                            Response.Listener<String> { response ->
                                Toast.makeText(
                                    context,
                                    "Se generó una nueva contraseña",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }, Response.ErrorListener { error ->
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                            }) {
                            override fun getParams(): MutableMap<String, String> {
                                val parametros = HashMap<String, String>()
                                parametros.put("nombres", etNombres.text.toString())
                                parametros.put("apellidos", etApellidos.text.toString())
                                parametros.put("correo", etCorreo.text.toString())
                                parametros.put("contraseña", etNuevaContraseA.text.toString())
                                return parametros
                            }

                        }
                        queue.add(resultadoPost)
                    }
                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al cambiar contraseña: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    private fun validarCampos(): Boolean {
        var valido = true

        val nombres = binding.etNombres.text.toString()
        val apellidos = binding.etApellidos.text.toString()
        val correo = binding.etCorreo.text.toString()
        val contraseña = binding.etNuevaContraseA.text.toString()

        if (nombres.isNullOrEmpty()) {
            binding.etNombres.setError("Por favor inserte un nombre")
            valido = false
        }
        if (apellidos.isNullOrEmpty()) {
            binding.etApellidos.setError("Por favor inserte un apellido")
            valido = false
        }
        if (correo.isNullOrEmpty()) {
            binding.etCorreo.setError("Por favor inserte un correo")
            valido = false
        }
        if (contraseña.isNullOrEmpty()) {
            binding.etNuevaContraseA.setError("Por favor rellene este campo")
            valido = false
        }
        return valido
    }
}

