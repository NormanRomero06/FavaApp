package com.example.appfavas.ui.historiales

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentHistorialInventarioBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Inventario.Inventario
import com.example.appfavas.modelos.Inventario.InventarioAdapter

class HistorialInventarioFragment : Fragment() {
    private lateinit var binding: FragmentHistorialInventarioBinding
    private var adapter: InventarioAdapter? = null
    private var recyclerView: RecyclerView? = null
    private val invList = arrayListOf<Inventario>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistorialInventarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupSearch()
        cargarInventario()
        return root
    }

    private fun cargarInventario() {
        val url = "http://localfavas.online/Inventario/ReadInventario.php"
        val reqQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(Request.Method.GET, url, null, { res ->
            val jsonArray = res.getJSONArray("data")

            invList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Inventario(
                    jsonObj.getInt("idInventario"),
                    jsonObj.getInt("Producto_idProducto"),
                    jsonObj.getString("tipoMovimiento"),
                    jsonObj.getInt("cantidad"),
                    jsonObj.getString("fecha")
                )
                invList.add(user)
            }
            println(invList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            recyclerView = binding.rvInventarioProd
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = context?.let { InventarioAdapter(invList, it) }
            recyclerView?.adapter = adapter

        }, {
            // Manejo de errores
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
                    cargarInventario()
                } else {
                    adapter?.filter(searchText)
                }
            }
        })
    }
}
