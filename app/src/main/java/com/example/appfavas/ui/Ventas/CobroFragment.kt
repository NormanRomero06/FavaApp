package com.example.appfavas.ui.Ventas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentCobroBinding
import com.example.appfavas.modelos.Ventas.PreSelect
import com.example.appfavas.modelos.Ventas.PreSelectAdapter


class CobroFragment : Fragment() {
   private lateinit var binding: FragmentCobroBinding

    val LisPreVenta = arrayListOf<PreSelect>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCobroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        llenarAdapter()
        return root
    }

    private fun llenarAdapter() {

        val idProducto = arguments?.getInt("idProducto")
        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getFloat("precio")

        val user = PreSelect(
            idProducto!!.toInt() , nombre!!.toString(), 20, precio!!.toFloat(),36f
        )
        LisPreVenta.add(user)
      binding.rcvProductos.layoutManager = LinearLayoutManager(context)
        binding.rcvProductos.adapter = PreSelectAdapter(LisPreVenta)



    }

    fun navigation()
    {
        binding.btnCobrar.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.nav_ventas)
            //Navigation.findNavController(binding.root).navigate(R.id.metodoDePagoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}