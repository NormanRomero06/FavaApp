package com.example.appfavas.ui.Categoria

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.appfavas.databinding.FragmentListaCategoriasBinding
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.modelos.Categoria
import com.example.appfavas.modelos.CategoriaAdapter


class ListaCategoriasFragment : Fragment() {
    private var _binding: FragmentListaCategoriasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val catList = arrayListOf<Categoria>()
    val uri ="http://localfavas.online/Categoria/ReadCategoria.php"
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCategoriasBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvCategoria
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request =JsonObjectRequest(Request.Method.GET, uri, null, {res ->
            val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Categoria(
                    jsonObj.getString("idCategoria"),
                    jsonObj.getString("nombre"),
                )
                catList.add(user)
            }
            println(catList.toString())

            recyclerView?.layoutManager = LinearLayoutManager(getActivity())
            recyclerView?.adapter = CategoriaAdapter(catList)


        },{
        })

        reqQueue.add(request)
    }

    fun navigation()
    {
        binding.btnNuevaCategoria.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.crearCategoriaFragment)
        }
        /*Este en teoría debería ser un click a una card para que abra
        * el editar categoría*/
        binding.rvCategoria.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.editarCategoriaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}