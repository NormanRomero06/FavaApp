package com.example.appfavas.ui.articulo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentTotalArticulosVentasBinding

class TotalArticulosVentasFragment : Fragment() {
    private var _binding: FragmentTotalArticulosVentasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTotalArticulosVentasBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation()
    {
        binding.btnNuevoArticulo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.crearArticuloVentasFragment)
        }
        /*Este en teoría debería ser un click a una card para que abra
        * el editar articulos*/
        binding.rvArticulos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.editar_EliminarArticulosVentasFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}