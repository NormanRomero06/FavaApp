package com.example.appfavas.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appfavas.databinding.FragmentRegistroUsuarioBinding
import com.example.appfavas.modelos.Usuario
import com.example.appfavas.modelos.viewModels.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject



class RegistroUsuarioFragment : Fragment() {

    // Declaración de variables
    private lateinit var binding: FragmentRegistroUsuarioBinding
    private lateinit var usuarioViewModel: UsuarioViewModel


    // Método de creación de la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el diseño de la vista del fragmento
        binding = FragmentRegistroUsuarioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inicializar ViewModel
        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)
        // Configurar el registro de usuario

        registrarUsuario()
        return root
    }

    // Método para registrar un usuario
    fun registrarUsuario() {
        // Configurar el botón de registro
        binding.btnRegister.setOnClickListener {
            try {
                // Obtener los valores de los campos de entrada y el spinner
                val nombres = binding.etNombres.text.toString()
                val apellidos = binding.etApellidos.text.toString()
                val correo = binding.etCorreo.text.toString()
                val usuario = binding.etUsuario.text.toString()
                val contraseña = binding.etContraseA.text.toString()
                val rol = binding.sRoles.selectedItem.toString()

                // Validar los campos
                validarCampos()

                // Crear objeto Usuario
                val user = Usuario(
                    nombres = nombres,
                    apellidos = apellidos,
                    correo = correo,
                    usuario = usuario,
                    contraseña = contraseña,
                    rol = rol
                )

                // Insertar usuario en la base de datos local
                CoroutineScope(Dispatchers.IO).launch {
                    usuarioViewModel.inserUs(user)
                }

                // Insertar usuario en la base de datos en la nube (MySQL) usando Volley
                val url = "http://localfavas.online/Usuario/InsertUsuario.php"
                val queue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        // La solicitud se completó correctamente
                        Toast.makeText(getActivity(),"Usuario ha sido insertado existosamente",Toast.LENGTH_LONG).show()
                    }, Response.ErrorListener { error ->
                        // Se produjo un error en la solicitud
                        Toast.makeText(getActivity(), "Error: $error", Toast.LENGTH_LONG).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val parametros = HashMap<String, String>()
                        parametros.put("nombres", nombres)
                        parametros.put("apellidos", apellidos)
                        parametros.put("correo", correo)
                        parametros.put("usuario", usuario)
                        parametros.put("contraseña", contraseña)
                        parametros.put("rol", rol)
                        return parametros
                    }
                }
                queue.add(resultadoPost)

            } catch (ex: Exception) {
                // Se produjo un error al insertar el usuario
                Toast.makeText(
                    requireContext(), "Error al Insertar: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    // Función para limpiar los campos de entrada después de una operación
    fun limpiarCampos() {
        with(binding) {
            etNombres.setText("")
            etApellidos.setText("")
            etCorreo.setText("")
            etUsuario.setText("")
            etContraseA.setText("")
        }
    }
    // Función para validar los campos de entrada
    fun validarCampos() {
        val nombres = binding.etNombres.text.toString()
        val apellidos = binding.etApellidos.text.toString()
        val correo = binding.etCorreo.text.toString()
        val usuario = binding.etUsuario.text.toString()
        val contraseña = binding.etContraseA.text.toString()

        // Validar que los campos no estén vacíos
        if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
   }
