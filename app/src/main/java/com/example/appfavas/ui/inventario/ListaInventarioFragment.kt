package com.example.appfavas.ui.inventario

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentListaInventarioBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Articulo.ArticuloAdapter


class ListaInventarioFragment : Fragment() {

    private lateinit var binding: FragmentListaInventarioBinding
    private val articuloList = arrayListOf<Articulo>()
    private val uri = "http://localfavas.online/Producto/ReadProducto.php"
    private var recyclerView: RecyclerView? = null
    private var adapter: ArticuloAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaInventarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cargarDatosArticulos()
        setupSearch()
        return root
    }

    private fun cargarDatosArticulos() {
        val reqQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            articuloList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val articulo = Articulo(
                    jsonObj.getInt("idProducto"),
                    jsonObj.getString("nombre"),
                    jsonObj.getString("descripcion"),
                    jsonObj.getDouble("precio").toFloat(),
                    jsonObj.getInt("cantidad"),
                    jsonObj.getInt("Categoria_idCategoria")
                )
                articuloList.add(articulo)
            }

            val spanCount = 2
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)

            recyclerView = binding.rvArticulo
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = ArticuloAdapter(articuloList)
            recyclerView?.adapter = adapter

        }, {
            // Manejar error de la solicitud HTTP si es necesario
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
                    cargarDatosArticulos()
                } else {
                    adapter?.filter(searchText)
                }
            }
        })
    }
}
