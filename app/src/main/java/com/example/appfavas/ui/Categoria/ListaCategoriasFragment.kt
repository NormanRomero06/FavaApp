package com.example.appfavas.ui.Categoria

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.example.appfavas.databinding.FragmentListaCategoriasBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.ArticuloAdapter
import com.example.appfavas.modelos.Categoria.Categoria
import com.example.appfavas.modelos.Categoria.CategoriaAdapter
import com.example.appfavas.modelos.Pago.PagoAdapter


class ListaCategoriasFragment : Fragment() {
    private lateinit var binding: FragmentListaCategoriasBinding
    private var adapter: CategoriaAdapter? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    val catList = arrayListOf<Categoria>()
    val uri = "http://localfavas.online/Categoria/ReadCategoria.php"
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaCategoriasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        setupSearch()
        cargarCategoria()
        return root
    }

    private fun cargarCategoria() {
        recyclerView = binding.rvCategoria
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            catList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Categoria(
                    jsonObj.getInt("idCategoria"),
                    jsonObj.getString("nombre"),
                )
                catList.add(user)
            }
            println(catList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView = binding.rvCategoria
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = CategoriaAdapter(catList)
            recyclerView?.adapter = adapter

        }, {
        })

        reqQueue.add(request)
    }

    fun navigation() {
            binding.fabNuevaCategoria.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.crearCategoriaFragment)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
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
                    cargarCategoria()
                } else {
                    adapter?.filter(searchText)
                }
            }
        })
    }
}