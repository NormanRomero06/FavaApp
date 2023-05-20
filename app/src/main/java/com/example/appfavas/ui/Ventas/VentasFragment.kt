package com.example.appfavas.ui.Ventas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentVentasHomeBinding

class VentasFragment : Fragment() {

    private var _binding: FragmentVentasHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentasHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    fun navigation()
    {
        binding.btnComprar.setOnClickListener {
           // Navigation.findNavController(binding.root).navigate(R.id.cobroFragmen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}