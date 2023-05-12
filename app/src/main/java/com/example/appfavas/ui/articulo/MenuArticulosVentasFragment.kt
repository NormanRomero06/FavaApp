package com.example.appfavas.ui.articulo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentMenuArticulosBinding


class MenuArticulosVentasFragment : Fragment() {
    private var _binding: FragmentMenuArticulosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuArticulosBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation()
    {
        binding.btnArticuloVenta.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.nav_articulos)
        }
        binding.btnCategoria.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.nav_categorias)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}