package com.example.appfavas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.appfavas.databinding.ActivityMainBinding
import com.example.appfavas.databinding.FragmentRegistroUsuarioBinding
import com.example.appfavas.modelos.Usuario
import com.example.appfavas.modelos.viewModels.UsuarioViewModel
import com.example.appfavas.ui.usuario.RegistroUsuarioFragment
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
           val fragment = RegistroUsuarioFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,  fragment)
                .addToBackStack(null).commit()

            binding.btnLogin.visibility = View.GONE
        }

    }

    override fun onBackPressed() {
        binding.btnLogin.visibility = View.VISIBLE
        super.onBackPressed()
    }



}

