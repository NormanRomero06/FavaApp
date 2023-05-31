package com.example.appfavas.ui.historiales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentHistorialesBinding

class historialesFragment : Fragment() {

    private var _binding: FragmentHistorialesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialesBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation()
    {
        binding.btnHArticulos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.historialInventarioFragment)
        }
        binding.btnHPagos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.historialPagos)
        }
        binding.btnHVentas.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.historialVentas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}