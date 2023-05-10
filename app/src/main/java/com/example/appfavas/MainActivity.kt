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
    private val viewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val iniciar = Intent(this, LayoutDrawableActivity::class.java)
            startActivity(iniciar)
        }


            val Nombres = "Pedro"
            val Apellidos ="Sin vista"
            val Correo = "NiIdea"
            val User = "NIko"
            val Pass = "123"
            val Rol = 1
            val usuario = Usuario(
                nombres = Nombres, apellidos = Apellidos, correo = Correo,
                usuario = User, contraseña = Pass, rol = Rol
            )

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.inserUs(usuario)
            }


        }


    }

