package com.example.appfavas.ui.usuario

import android.content.Context
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentUsuariosBinding
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Usuario.Usuario
import com.example.appfavas.modelos.Usuario.UsuarioAdapter
import com.example.appfavas.modelos.viewModels.UsuarioViewModel
import java.io.Console

// Función para obtener el ID y rol del usuario
class UsuariosFragment : Fragment() {
    private lateinit var binding: FragmentUsuariosBinding
    private val userList = arrayListOf<Usuario>()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        recyclerView = binding.rvUsuarios

        obtenerRolUsuario()

        return binding.root
    }

    private fun obtenerRolUsuario() {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("idUsuario", -1)

        // Utiliza el idUsuario para hacer la solicitud a la API y obtener el rol
        val url = "http://localfavas.online/Usuario/IDC_Usuario.php?idUsuario=$idUsuario"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.d("TAG", response.toString()) // Imprimir la respuesta completa
                val rol = response.optString("rol") // Obtener el valor del campo "rol" como cadena vacía si no está presente

                if (rol == "Administrador") {
                    // Lógica para el rol de Administrador
                    cargarUsuariosAdmin()
                    println("Hola Admin")
                    // ...
                } else if (rol == "Cocina") {
                    // Lógica para el rol de Cocina
                    cargarUsuariosOther()
                    println("Hola Cocina")
                    // ...
                }
            },
            { error ->
                // Manejar el error de la solicitud
                Log.e("TAG", "Error en la solicitud: ${error.message}")
            })

        // Agregar la solicitud a la cola de Volley
        Volley.newRequestQueue(requireContext()).add(request)
    }



    private fun cargarUsuariosOther() {
        val url = "http://localfavas.online/Usuario/ReadOtherUser.php"

        val requestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("data")
                //val userList = arrayListOf<Usuario>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObj = jsonArray.getJSONObject(i)
                    val idUsuario = jsonObj.getInt("idUsuario")
                    val nombres = jsonObj.getString("nombres")
                    val apellidos = jsonObj.getString("apellidos")
                    val correo = jsonObj.getString("correo")
                    val rol = jsonObj.getString("rol")

                    // Verificar si el rol es "Cocina"
                    if (rol == "Cocina") {
                        val user = Usuario(idUsuario, nombres, apellidos, correo, "", "", rol)
                        userList.add(user)
                    }
                }

                // Actualizar el RecyclerView con la lista de usuarios de cocina
                recyclerView?.layoutManager = LinearLayoutManager(requireContext())
                recyclerView?.adapter = UsuarioAdapter(userList)

            },
            { error ->
                Log.d("Error", error.toString())
            })

        requestQueue.add(request)
    }


    private fun cargarUsuariosAdmin() {
        val uri = "http://localfavas.online/Usuario/ReadUsuario.php"

        recyclerView = binding.rvUsuarios

        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Usuario(
                    jsonObj.getInt("idUsuario"),
                    jsonObj.getString("nombres"),
                    jsonObj.getString("apellidos"),
                    jsonObj.getString("correo"),
                    jsonObj.getString("usuario"),
                    jsonObj.getString("contraseña"),
                    jsonObj.getString("rol")
                )
                userList.add(user)
            }
            println(userList.toString())

            recyclerView?.layoutManager = LinearLayoutManager(getActivity())
            recyclerView?.adapter = UsuarioAdapter(userList)

        }, { err ->
            Log.d("Fail...", err.message.toString())
        })

        reqQueue.add(request)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}


