package com.example.appfavas.ui.Ventas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentVistasVentaBinding
import com.example.appfavas.modelos.Ventas.Ventas
import com.example.appfavas.modelos.Ventas.VentasAdapter

class VistaVentasFragment : Fragment() {

    private lateinit var binding: FragmentVistasVentaBinding
    private val ventasList = arrayListOf<Ventas>()
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVistasVentaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navigation()
        cargarVentas()
        return root
    }

    private fun navigation() {
        // Aquí configura la navegación en caso de ser necesario
    }

    private fun cargarVentas() {

        val uri = "http://localfavas.online/Venta/ReadVenta.php"

        recyclerView = binding.rvVentas
        val reqQueuee: RequestQueue = Volley.newRequestQueue(requireActivity())
        val requeste = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            ventasList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Ventas(
                    jsonObj.getInt("idVenta"),
                    jsonObj.getDouble("Total_Venta").toFloat(),
                    jsonObj.getString("Usuario"),
                )
                ventasList.add(user)
            }
            Log.d("Ventas List", ventasList.toString())

            recyclerView?.layoutManager = GridLayoutManager(activity, 2)
            recyclerView?.adapter = context?.let { VentasAdapter(ventasList, it) }


        }, { err ->
            Log.d("Volley fail", err.message.toString())
        })

        reqQueuee.add(requeste)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
