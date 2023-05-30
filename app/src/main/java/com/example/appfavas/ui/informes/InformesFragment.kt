package com.example.appfavas.ui.informes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    fun navigation()
    {
        binding.btnIArticulos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeArticulos)
        }
        /*
        binding.btnIPagos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformePagos)
        }
        binding.btnIVentas.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.InformeVentas)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}