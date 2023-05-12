package com.example.appfavas.ui.recibos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentRecibosBinding

class RecibosFragment : Fragment() {

    private var _binding: FragmentRecibosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecibosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        return root
    }

    fun navigation()
    {
        /*Este en teoría debería ser un click a una card para que abra
        * el editar articulos*/
        binding.rvEfectivo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.devolucionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}