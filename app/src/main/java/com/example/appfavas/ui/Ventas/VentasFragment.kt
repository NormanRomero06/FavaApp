package com.example.appfavas.ui.Ventas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentVentasHomeBinding
import com.example.appfavas.modelos.Articulo.ArticuloVentas
import com.example.appfavas.modelos.Articulo.ArticuloVentasAdapter
import com.example.appfavas.modelos.Categoria.CategoriaAdapter

class VentasFragment : Fragment() {
    private lateinit var binding: FragmentVentasHomeBinding
    private var adapter: ArticuloVentasAdapter? = null
    var recyclerView: RecyclerView? = null
    val artList = arrayListOf<ArticuloVentas>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVentasHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        setupSearch()
        cargarProductos()
        return root
    }

    private fun cargarProductos() {

        val url = "http://localfavas.online/Producto/ReadProducto.php"

        recyclerView = binding.rcvProductos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, url, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            artList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val users = ArticuloVentas(
                    jsonObj.getInt("idProducto"),
                    jsonObj.getString("nombre"),
                    jsonObj.getDouble("precio").toFloat(),
                )
                artList.add(users)
            }
            println(artList.toString())

            adapter = ArticuloVentasAdapter(artList)
            recyclerView?.layoutManager = GridLayoutManager(getActivity(), 2) // Mostrar dos columnas
            recyclerView?.adapter = adapter

        }, { err ->
            Log.d("Volley fail", err.message.toString())
        })

        reqQueue.add(request)
    }

    private fun setupSearch() {
        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se requiere implementación
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se requiere implementación
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                if (searchText.isEmpty()) {
                    // Si el texto de búsqueda está vacío, cargar los datos completos del RecyclerView
                    cargarProductos()
                } else {
                    adapter?.filter(searchText)
                }
            }
        })
    }



    fun navigation() {
        binding.btnComprar.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.cobroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}