package com.example.appfavas.ui.articulo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentTotalArticulosVentasBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Articulo.ArticuloAdapter
import com.example.appfavas.modelos.Articulo.ArticuloVista
import com.example.appfavas.modelos.Articulo.ArticuloVistaAdapter
import org.json.JSONObject

class TotalArticulosVentasFragment : Fragment() {
    private lateinit var binding: FragmentTotalArticulosVentasBinding
    val artList = arrayListOf<Articulo>()
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTotalArticulosVentasBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()
        cargarProductos()
        return root
    }

     fun cargarProductos(){

            val uri ="http://localfavas.online/Producto/ReadData.php"

            recyclerView = binding.rvArticulos
            val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
            val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
                val jsonArray = res.getJSONArray("data")

                //Limpia la lista para evitar items duplicados
                artList.clear()
                for (i in 0 until jsonArray.length()){
                    val jsonObj = jsonArray.getJSONObject(i)
                    val user = Articulo(
                        jsonObj.getInt("idProducto"),
                        jsonObj.getString("nombre"),
                        jsonObj.getString("descripcion"),
                        jsonObj.getDouble("precio").toFloat(),
                        jsonObj.getInt("cantidad"),
                        //jsonObj.getString("imagen")
                        //jsonObj.getString("Categoria_Nombre")

                    )
                    artList.add(user)
                }
                println(artList.toString())

                val spanCount = 2 // Número de columnas en la cuadrícula
                val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
                val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
                val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
                val space = resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
                recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
                recyclerView?.layoutManager = gridLayoutManager
                recyclerView?.adapter = ArticuloAdapter(artList)



            },{

            })

            reqQueue.add(request)
        }

    fun navigation()
    {
        binding.btnNuevoArticulo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.crearArticuloVentasFragment)
        }
        /*Este en teoría debería ser un click a una card para que abra
        * el editar articulos*/
        binding.rvArticulos.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.editar_EliminarArticulosVentasFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}