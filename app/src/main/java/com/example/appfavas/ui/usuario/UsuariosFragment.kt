package com.example.appfavas.ui.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentUsuariosBinding
import com.example.appfavas.modelos.Usuario
import com.example.appfavas.modelos.UsuarioAdapter
import com.example.appfavas.modelos.viewModels.UsuarioViewModel

class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsuariosBinding? = null
    private lateinit var usuarioAdapter: UsuarioAdapter
    private var usuarioSeleccionado: Usuario? = null
    private val viewModel: UsuarioViewModel by viewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)

        val root: View = binding.root

        navigation()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsuarios.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usuarioAdapter
        }

        viewModel.todos.observe(viewLifecycleOwner, Observer { usuarios ->
            usuarioAdapter.setUsuarios(usuarios)
        })
    }


        fun navigation()
    {
        binding.btnNuevoUsuario.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registroUsuarioFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}