package com.example.appfavas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.ActivityMainBinding
import com.example.appfavas.ui.usuario.RecuperarPWFragment
import com.example.appfavas.ui.usuario.RegistroUsuarioFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        registrarse()
        recuperarUser()

    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            var usuario = binding.txtUsuario.editText?.text.toString()
            var contraseña = binding.txtContraseA.editText?.text.toString()

            val uri = "http://localfavas.online/Usuario/LoginValidation.php"
            val stringRequest = object : StringRequest(Request.Method.POST, uri,
                Response.Listener<String> { response ->
                    if (response == "Validado") {
                        // El usuario y contraseña son válidos
                        Log.d("TAG", "Login exitoso")
                        val iniciar = Intent(this, LayoutDrawableActivity::class.java)
                        startActivity(iniciar)
                    } else {
                        // El usuario y/o contraseña son inválidos
                        Log.d("TAG", "Login fallido")
                        Toast.makeText(this,"Error en los datos", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("TAG", "Error en la solicitud: ${error.message}")
                }) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["usuario"] = usuario
                    params["contraseña"] = contraseña
                    return params
                }
            }

            // Agrega la solicitud a la cola de solicitudes
            Volley.newRequestQueue(this).add(stringRequest)

        }
    }



    private fun registrarse() {
        binding.TvRegistrarse.setOnClickListener {
            val fragment = RegistroUsuarioFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commit()

            binding.btnLogin.visibility = View.GONE
        }
    }

    private fun recuperarUser(){
        binding.TvOlvidarPW.setOnClickListener {
            val fragmentR = RecuperarPWFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentR)
                .addToBackStack(null).commit()

            binding.btnLogin.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        binding.btnLogin.visibility = View.VISIBLE
        super.onBackPressed()
    }


}

