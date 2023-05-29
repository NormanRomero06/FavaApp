package com.example.appfavas.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.databinding.FragmentCheckProductoBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckProductoFragment : Fragment() {
    private lateinit var binding: FragmentCheckProductoBinding
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckProductoBinding.inflate(inflater, container, false)
        database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        obtenerDatos()
        binding.btnSubmit.setOnClickListener {
            guardarDatosEnRoom()
        }
        return binding.root
    }

    private fun obtenerDatos() {
        val idProducto = arguments?.getString("idProducto")
        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getString("precio")
        val cantidad = arguments?.getInt("cantidad", 0) ?: 0
        val precioTotal = arguments?.getString("precioTotal")

        // Verificar si los valores no son nulos antes de asignarlos a los TextViews
        if (!idProducto.isNullOrEmpty()) {
            binding.tvidProductoInsert.text = idProducto
        }
        if (!nombre.isNullOrEmpty()) {
            binding.tvNombreInsert.text = nombre
        }
        if (!precio.isNullOrEmpty()) {
            binding.tvPrecioUnidadInsert.text = precio
        }
        if (cantidad != 0) {
            binding.tvCantidadVenInsert.text = cantidad.toString()
        }
        if (!precioTotal.isNullOrEmpty()) {
            binding.tvPreciototalInsert.text = precioTotal
        }
    }

    private fun guardarDatosEnRoom() {
        val idProducto = binding.tvidProductoInsert.text.toString().toInt()
        val nombre = binding.tvNombreInsert.text.toString()
        val precio = binding.tvPrecioUnidadInsert.text.toString().toFloat()
        val cantidad = binding.tvCantidadVenInsert.text.toString().toInt()
        val precioTotal = binding.tvPreciototalInsert.text.toString().toFloat()

        val inventarioTemp = InventarioTemp(
            idProducto = idProducto,
            nombre = nombre,
            precio = precio,
            cantidadVenta = cantidad,
            precioTotal = precioTotal
        )

        lifecycleScope.launch(Dispatchers.IO) {
            database.inventarioTempDao().insert(inventarioTemp)

            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Producto guardado exitosamente", Toast.LENGTH_SHORT).show()
                // Otras acciones o actualizaciones de la interfaz de usuario
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}




