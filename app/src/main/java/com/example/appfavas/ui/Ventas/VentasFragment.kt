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
import com.example.appfavas.modelos.Categoria
import com.example.appfavas.modelos.CategoriaAdapter

class VentasFragment : Fragment() {

    private var _binding: FragmentVentasHomeBinding? = null

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
        _binding = FragmentVentasHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        navigation()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvCategoria
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")
            Log.d("Volley Sample",jsonArray.toString())

            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                Log.d("Volley Sample",jsonObj.toString())
                val user = Categoria(
                    jsonObj.getString("idCategoria"),
                    jsonObj.getString("nombre"),
                )
                Log.d("Perfecto", catList.toString())
                catList.add(user)
            }
            Log.d("Ideal", catList.toString())
            println(catList.toString())

            recyclerView?.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView?.adapter = CategoriaAdapter(catList)

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
        _binding = null
    }
}