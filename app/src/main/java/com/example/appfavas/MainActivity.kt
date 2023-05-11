package com.example.appfavas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.appfavas.databinding.ActivityMainBinding
import com.example.appfavas.modelos.Usuario
import com.example.appfavas.modelos.viewModels.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val iniciar = Intent(this, LayoutDrawableActivity::class.java)
            startActivity(iniciar)
        }

        binding.TvRegistrarse.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registroUsuarioFragment)
        }




        }


    }

