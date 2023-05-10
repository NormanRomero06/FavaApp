package com.example.appfavas.ui.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentRegistroUsuarioBinding
import com.example.appfavas.modelos.Usuario
import com.example.appfavas.modelos.viewModels.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistroUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentRegistroUsuarioBinding
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistroUsuarioBinding.inflate(inflater, container, false)

        val root: View = binding.root

        // Inicializar ViewModel
        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        // Configurar botón de guardar
        binding.btnRegister.setOnClickListener {
            try {
                val nombres = binding.etNombres.text.toString()
                val apellidos = binding.etApellidos.text.toString()
                val correo = binding.etCorreo.text.toString()
                val usuario = binding.etUsuario.text.toString()
                val contraseña = binding.etContraseA.text.toString()
                val rol = binding.sRoles.selectedItem.toString().toInt()

                // Validar que los campos no estén vacíos
                if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Por favor complete todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // Crear objeto Usuario
                val user = Usuario(
                    nombres = nombres,
                    apellidos = apellidos,
                    correo = correo,
                    usuario = usuario,
                    contraseña = contraseña,
                    rol = rol
                )

                // Insertar usuario en la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    usuarioViewModel.inserUs(user)
                }

                // Mostrar mensaje de éxito
                Toast.makeText(
                    requireContext(),
                    "Usuario registrado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()

                // Limpiar campos
                binding.etNombres.setText("")
                binding.etApellidos.setText("")
                binding.etCorreo.setText("")
                binding.etUsuario.setText("")
                binding.etContraseA.setText("")
            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(), "Error al Insertar: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }




        return root
    }
}