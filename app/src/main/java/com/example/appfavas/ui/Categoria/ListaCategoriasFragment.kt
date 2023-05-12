package com.example.appfavas.ui.Categoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentListaCategoriasBinding


class ListaCategoriasFragment : Fragment() {
    private var _binding: FragmentListaCategoriasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCategoriasBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation()
    {
        binding.btnNuevaCategoria.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.crearCategoriaFragment)
        }
        /*Este en teoría debería ser un click a una card para que abra
        * el editar categoría*/
        binding.rvCategoria.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.editarCategoriaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}