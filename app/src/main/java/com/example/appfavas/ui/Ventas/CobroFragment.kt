package com.example.appfavas.ui.Ventas

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.databinding.FragmentCobroBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp
import com.example.appfavas.modelos.InventarioTemp.InventarioTempAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CobroFragment : Fragment(), InventarioTempAdapter.OnItemClickListener {
    private lateinit var binding: FragmentCobroBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: InventarioTempAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCobroBinding.inflate(inflater, container, false)
        database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        setupRecyclerView()
        setupCobrarButton()
        binding.btnEliminar.setOnClickListener {
            eliminarProductos()
        }

        return binding.root
    }
    override fun onDeleteClicked(inventarioTemp: InventarioTemp) {
        // Aquí debes eliminar el elemento de la base de datos usando el DAO correspondiente
        lifecycleScope.launch(Dispatchers.IO) {
            database.inventarioTempDao().delete(inventarioTemp)
        }
    }

    private fun eliminarProductos() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.inventarioTempDao().deleteAll()
            }
            adapter.setData(emptyList()) // Vaciar la lista de productos en el adaptador
            adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            binding.tvMonto.text = "0.00" // Restablecer el monto total a cero
        }
    }


    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.rcvProductos
        adapter = InventarioTempAdapter()
        recyclerView.adapter = adapter

        val columnCount = 2
        val layoutManager = GridLayoutManager(requireContext(), columnCount)
        recyclerView.layoutManager = layoutManager

        lifecycleScope.launch(Dispatchers.Main) {
            val data = withContext(Dispatchers.IO) { database.inventarioTempDao().getAll() }
            adapter.setData(data)
            adapter.calcularMontoTotal()
            binding.tvMonto.text = String.format("%.2f", adapter.montoTotal)
            saveMontoTotalToSharedPreferences(adapter.montoTotal) // Almacenar el montoTotal en SharedPreferences
        }
        adapter.setOnItemClickListener(this)
    }

    private fun saveMontoTotalToSharedPreferences(montoTotal: Float) {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("montoTotal", montoTotal)
        editor.apply()
    }

    private fun setupCobrarButton() {
        binding.btnCobrar.setOnClickListener {
            insertarVenta()
        }
    }

    private fun insertarVenta() {
        try {
            val productos = adapter.getData()

            val url = "http://localfavas.online/Venta/InsertVenta.php"
            val queue = Volley.newRequestQueue(requireContext())
            val resultadoPost = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Log.d("InsertarVentas", "Respuesta del servidor: $response")
                    val idVenta = response.toIntOrNull()
                    if (idVenta != null) {
                        guardarIdVentaEnSharedPreferences(idVenta)
                        Toast.makeText(
                            requireContext(),
                            "Venta realizada exitosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        for (producto in productos) {
                            val cantidadVendida = producto.cantidadVenta
                            val precioUnitario = producto.precio
                            val idProducto = producto.idProducto

                            insertarDetallesVenta(cantidadVendida, precioUnitario, idProducto)
                            insertarInventario(idProducto, cantidadVendida)
                        }
                        eliminarProductos()
                        // Navegar a la siguiente pantalla después de realizar el cobro
                        findNavController().navigate(R.id.metodoDePagoFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al obtener el ID de la venta",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("InsertarVentas", "Error en la solicitud: ${error.message}")
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["totalVenta"] = binding.tvMonto.text.toString()

                    val idUsuario = obtenerIdUsuarioDesdeSharedPreferences()
                    params["Usuario_idUsuario"] = idUsuario.toString()

                    return params
                }
            }
            queue.add(resultadoPost)
        } catch (ex: Exception) {
            Toast.makeText(
                requireContext(),
                "Error al insertar: ${ex.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun insertarInventario(idProducto: Any, cantidadVendida: Any){

        val url = "http://localfavas.online/Inventario/ActualizarEntradas.php"
        val queue = Volley.newRequestQueue(activity)
        val resultadoPost = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(
                    context,
                    "Insertado exitosamente",
                    Toast.LENGTH_LONG
                ).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("Producto_idProducto", idProducto.toString())
                parametros.put("tipoMovimiento", "Salida")
                parametros.put("cantidad", cantidadVendida.toString())
                Log.d("Prueba1", "Parametros:$parametros")//Prueba
                return parametros

            }
        }
        queue.add(resultadoPost)
    }

    private fun insertarDetallesVenta(cantidadVendida: Any, precioUnitario: Any, idProducto: Any) {
        try {
            val url = "http://localfavas.online/DetallesVentas/InsertDetallesVenta.php"
            val queue = Volley.newRequestQueue(requireContext())
            val resultadoPost = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(
                        requireContext(),
                        "Insertado exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()

                    val idVenta = obtenerIdVentaDesdeSharedPreferences()
                    params["Venta_idVenta"] = idVenta.toString()
                    params["cantidadVendida"] = cantidadVendida.toString()
                    params["precioUnitario"] = precioUnitario.toString()
                    params["Producto_idProducto"] = idProducto.toString()

                    return params
                }
            }
            queue.add(resultadoPost)
        } catch (ex: Exception) {
            Toast.makeText(
                requireContext(),
                "Error al insertar: ${ex.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun guardarIdVentaEnSharedPreferences(idVenta: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("idVenta", idVenta)
        editor.apply()
    }

    private fun obtenerIdUsuarioDesdeSharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idUsuario", 0)
    }

    private fun obtenerIdVentaDesdeSharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idVenta", 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}

