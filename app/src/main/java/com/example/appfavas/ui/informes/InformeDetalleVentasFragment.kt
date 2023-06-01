package com.example.appfavas.ui.informes

import android.os.Bundle
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
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformeDetalleVentasBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Ventas.DetalleVentaInforme
import com.example.appfavas.modelos.Ventas.DetalleVentaInformeAdapter


class InformeDetalleVentasFragment : Fragment() {

    private lateinit var binding: FragmentInformeDetalleVentasBinding
    val detalleVentaList = arrayListOf<DetalleVentaInforme>()
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformeDetalleVentasBinding.inflate(inflater,container, false)

        val root: View = binding.root
        cargarProductos()
        return root
    }

    private fun cargarProductos() {
        val uri = "http://localfavas.online/DetallesVentas/ReadDetalleVentas.php"

        recyclerView = binding.rvInformeVentas
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            detalleVentaList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = DetalleVentaInforme(
                    jsonObj.getString("Producto"),
                    jsonObj.getInt("Cantidad_Vendida"),
                    jsonObj.getDouble("Precio_Unitario").toFloat(),
                    jsonObj.getInt("Total_Detalle_Venta"),
                    jsonObj.getInt("Codigo_de_Venta"),
                    jsonObj.getInt("Total_Venta"),
                    jsonObj.getString("Usuario")
                )
                detalleVentaList.add(user)
            }
            println(detalleVentaList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = DetalleVentaInformeAdapter(detalleVentaList)


        }, {

        })

        reqQueue.add(request)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

}