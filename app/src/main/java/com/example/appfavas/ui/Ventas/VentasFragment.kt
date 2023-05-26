package com.example.appfavas.ui.Ventas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentVentasHomeBinding
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Articulo.ArticuloVentas
import com.example.appfavas.modelos.Articulo.ArticuloVentasAdapter
import com.example.appfavas.modelos.Categoria.CategoriaVentas
import com.example.appfavas.modelos.Categoria.CategoriaVentasAdapter

class VentasFragment : Fragment() {

    private lateinit var binding: FragmentVentasHomeBinding
    var recyclerView: RecyclerView? = null
    var recyclerVie: RecyclerView? = null
    val catList = arrayListOf<CategoriaVentas>()
    val artList = arrayListOf<ArticuloVentas>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVentasHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        cargarProductos()
        cargarCategoria()

        return root
    }



   private fun cargarCategoria(){

        val uri ="http://localfavas.online/Categoria/ReadCategoria.php"

        recyclerVie = binding.rvCategoria
        val reqQueuee: RequestQueue = Volley.newRequestQueue(getActivity())
        val requeste = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            catList.clear()
            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                val user = CategoriaVentas(
                    jsonObj.getInt("idCategoria"),
                    jsonObj.getString("nombre"),
                )
                catList.add(user)
            }
            println(catList.toString())

            recyclerVie?.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerVie?.adapter = CategoriaVentasAdapter(catList)

        },{err ->
            Log.d("Volley fail", err.message.toString())
        })

        reqQueuee.add(requeste)
    }

    private fun cargarProductos() {

        val url ="http://localfavas.online/Producto/ReadProducto.php"

        recyclerView = binding.rcvProductos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, url, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            artList.clear()
            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                val users = ArticuloVentas(
                    jsonObj.getInt("idProducto"),
                    jsonObj.getString("nombre"),
                    jsonObj.getDouble("precio").toFloat(),
                )
                artList.add(users)
            }
            println(artList.toString())

            recyclerView?.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView?.adapter = ArticuloVentasAdapter(artList)

        },{err ->
            Log.d("Volley fail", err.message.toString())
        })

        reqQueue.add(request)
    }


    fun navigation()
    {
        binding.btnComprar.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.cobroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

}