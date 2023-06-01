package com.example.appfavas.ui.informes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformesBinding

class InformesFragment : Fragment() {
    private var _binding: FragmentInformesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformesBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation() {
        binding.btnIArticulos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeArticulos)
        }

        binding.btnIPagos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformePagos)
        }
        binding.btnIInventario.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeInventario)
        }
        binding.btnICierreCaja.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeCierreCaja)
        }
        binding.btnIDetalleVenta.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeDetalleVentas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}