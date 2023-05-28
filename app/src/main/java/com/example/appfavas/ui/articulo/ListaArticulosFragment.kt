package com.example.appfavas.ui.articulo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentListaArticulosBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Articulo.ArticuloAdapter


class ListaArticulosFragment : Fragment() {
    private lateinit var binding: FragmentListaArticulosBinding
    val artList = arrayListOf<Articulo>()
    var recyclerView: RecyclerView? = null
    private var adapter: ArticuloAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaArticulosBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()
        setupSearch()
        cargarProductos()
        return root
    }

    fun cargarProductos() {

        val uri = "http://localfavas.online/Producto/ReadProducto.php"

        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            artList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Articulo(
                    jsonObj.getInt("idProducto"),
                    jsonObj.getString("nombre"),
                    jsonObj.getString("descripcion"),
                    jsonObj.getDouble("precio").toFloat(),
                    jsonObj.getInt("cantidad"),
                    //jsonObj.getString("imagen")
                    jsonObj.getInt("Categoria_idCategoria"),
                )
                artList.add(user)
            }
            println(artList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView = binding.rvArticulos
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = ArticuloAdapter(artList)
            recyclerView?.adapter = adapter


        }, {

        })

        reqQueue.add(request)
    }

    fun navigation()
    {
        binding.fabNuevoArticulo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.crearArticuloVentasFragment)
        }

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


    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}