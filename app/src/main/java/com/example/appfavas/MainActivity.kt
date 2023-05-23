package com.example.appfavas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            val iniciar = Intent(this, LayoutDrawableActivity::class.java)
            startActivity(iniciar)
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

