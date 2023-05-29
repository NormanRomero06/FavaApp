package com.example.appfavas.ui.pagos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.appfavas.databinding.FragmentListaPagosBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.ArticuloAdapter
import com.example.appfavas.modelos.Pago.Pago
import com.example.appfavas.modelos.Pago.PagoAdapter

class ListaPagosFragment : Fragment() {
    private var _binding: FragmentListaPagosBinding? = null
    private var adapter: PagoAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val pagoList = arrayListOf<Pago>()
    val uri = "http://localfavas.online/Egresos/ReadEgresos.php"
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaPagosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupSearch()
        cargarPagos()
        return root
    }

    private fun cargarPagos(){
        val reqQueue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            pagoList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Pago(
                    jsonObj.getInt("idPagos"),
                    jsonObj.getString("descripcion"),
                    jsonObj.getString("monto").toFloat(),
                    jsonObj.getString("fechaHora")
                )
                pagoList.add(user)
            }
            println(pagoList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView = binding.rvPagos
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = PagoAdapter(pagoList)
            recyclerView?.adapter = adapter

        }, {
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
                    cargarPagos()
                } else {
                    adapter?.filter(searchText)
                }
            }
        })
    }


}