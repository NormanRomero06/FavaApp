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

    private lateinit var binding:FragmentListaInventarioBinding
    private val articuloList = arrayListOf<Articulo>()
    private val uri = "http://localfavas.online/Producto/ReadProducto.php"
    private var recyclerView: RecyclerView? = null
    private var adapter: ArticuloAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListaInventarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cargarDatosArticulos()
        buscarArticulo()
        return root
    }


    private fun cargarDatosArticulos() {
        val reqQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            // Limpia la lista para evitar elementos duplicados
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

            recyclerView = binding.rvArticulo
            Log.VERBOSE

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)

            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = ArticuloAdapter(articuloList)
            recyclerView?.adapter = adapter

            adapter?.notifyDataSetChanged()

        }, {
            // Manejar error de la solicitud HTTP si es necesario
        })

        reqQueue.add(request)

    }

    fun buscarArticulo() {
        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("Que esta pasandoooo",p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No es necesario implementar esto
            }

            override fun afterTextChanged(p0: Editable?) {
                val searchText = p0.toString()
                adapter?.filter(searchText)
            }
        })

    }


}