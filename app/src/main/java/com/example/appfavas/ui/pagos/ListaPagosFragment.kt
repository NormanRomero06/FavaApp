package com.example.appfavas.ui.pagos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentListaPagosBinding
import com.example.appfavas.modelos.Pago.Pago
import com.example.appfavas.modelos.Pago.PagoAdapter

class ListaPagosFragment : Fragment() {
    private var _binding: FragmentListaPagosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val pagoList = arrayListOf<Pago>()
    val uri ="http://localfavas.online/Egresos/ReadEgresos.php"
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaPagosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvPagos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Pago(
                    jsonObj.getString("descripcion"),
                    jsonObj.getString("monto").toFloat(),
                    jsonObj.getString("fechaHora")
                )
                pagoList.add(user)
            }
            println(pagoList.toString())

            recyclerView?.layoutManager = LinearLayoutManager(getActivity())
            recyclerView?.adapter = PagoAdapter(pagoList)


        },{
        })
        reqQueue.add(request)
    }

}