package com.example.appfavas.ui.Ventas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.appfavas.R
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.databinding.FragmentCobroBinding
import com.example.appfavas.modelos.InventarioTemp.InventarioTempAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CobroFragment : Fragment() {
    private lateinit var binding: FragmentCobroBinding
    private lateinit var database: AppDatabase
    //val recyclerView: RecyclerView = binding.rcvProductos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCobroBinding.inflate(inflater, container, false)
        database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        val root: View = binding.root
        setupRecyclerView()
        navigation()
        //insertDVentas()

        return root
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.rcvProductos
        val adapter = InventarioTempAdapter()
        recyclerView.adapter = adapter

        // Configurar el GridLayoutManager con el número de columnas deseadas
        val columnCount = 2 // Número de columnas
        val layoutManager = GridLayoutManager(requireContext(), columnCount)
        recyclerView.layoutManager = layoutManager

        lifecycleScope.launch(Dispatchers.Main) {
            val data = withContext(Dispatchers.IO) { database.inventarioTempDao().getAll() }
            adapter.setData(data)
            adapter.calcularMontoTotal()
            binding.tvMonto.text = String.format("%.2f", adapter.montoTotal)
        }
    }

    fun navigation() {
        binding.btnCobrar.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.nav_ventas)
            //Navigation.findNavController(binding.root).navigate(R.id.metodoDePagoFragment)
        }
    }

    /*private fun insertDVentas() {
        binding.btnCobrar.setOnClickListener {
            try {
                var cantidadVendida: Int
                var precioUnitario: String
                var productoid: String

                val adaptador = recyclerView.adapter as InventarioTempAdapter
                val productosSeleccionados = adaptador.getProductosSeleccionados()

                for (producto in productosSeleccionados) {
                    val cantidadVendida = producto.cantidadVendida.toInt()
                    val precioUnitario = producto.precioUnitario
                    val productoid = producto.productoId.toString()
                }

                /*val cantProd = view?.findViewById<TextView>(R.id.tvCantProdTotal)
                val cantidadVendida = cantProd?.text.toString().toInt()
                val precio = view?.findViewById<TextView>(R.id.tvPUnitario)
                val precioUnitario = precio?.text.toString()
                val producto = view?.findViewById<TextView>(R.id.tvidProd)
                val productoid = producto?.text.toString().toInt()*/

                    val url = "http://localfavas.online/DetallesVentas/InsertDetallesVenta.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Insertado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String, String>()
                            params["cantidadVendida"] = cantidadVendida.toString()
                            params["precioUnitario"] = precioUnitario
                            params["Producto_idProducto"] = productoid.toString()
                            return params
                        }
                    }
                    queue.add(resultadoPost)
            }catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error al insertar: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
