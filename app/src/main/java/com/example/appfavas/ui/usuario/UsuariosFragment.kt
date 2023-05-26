package com.example.appfavas.ui.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentUsuariosBinding
import com.example.appfavas.modelos.Usuario.Usuario
import com.example.appfavas.modelos.Usuario.UsuarioAdapter
import com.example.appfavas.modelos.viewModels.UsuarioViewModel

class UsuariosFragment : Fragment() {
    //private var _binding: FragmentUsuariosBinding? = null
    /*var recyclerUsuario: RecyclerView? = null
    var usuarios: List<Usuario>? = null
    var adapter: UsuarioAdapter? = null*/
    private val viewModel: UsuarioViewModel by viewModels()
    private lateinit var usuarioAdapter: UsuarioAdapter
    private var usuarioSeleccionado: Usuario? = null
    private lateinit var binding: FragmentUsuariosBinding


    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        navigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUsuariosBinding.bind(view)

        usuarioAdapter = UsuarioAdapter { usuario ->
            usuarioSeleccionado = usuario
        }
        /*usuarioAdapter = UsuarioAdapter { usuario ->
            usuarioSeleccionado = usuario
            binding.etNombres.setText(estudiante.nombres)
            binding.etApellidos.setText(estudiante.apellidos)
            binding.etCarrera.setText(estudiante.carrera)
            binding.etAnio.setText(estudiante.anio.toString())
        }*/
        binding.rvUsuarios.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usuarioAdapter
        }

        viewModel.todos.observe(viewLifecycleOwner) { usuarios ->
            usuarioAdapter.setUsuarios(usuarios)
        }
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerUsuario = view.findViewById(R.id.rvUsuarios)

        lifecycleScope.launch {
            usuarios = app!!.app_database
        }

        adapter = UsuarioAdapter(usuarios!!, requireContext())

        recyclerUsuario!!.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerUsuario!!.setHasFixedSize(true)
        recyclerUsuario!!.adapter =
    }*/


        fun navigation() {
            binding.fabNuevoUsuario.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.registroUsuarioFragment)
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding
        }
    }
