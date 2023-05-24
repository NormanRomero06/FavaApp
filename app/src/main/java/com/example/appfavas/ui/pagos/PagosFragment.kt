package com.example.appfavas.ui.pagos

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentPagosBinding

class PagosFragment : Fragment() {

    private lateinit var binding: FragmentPagosBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPagosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        realizarPago()

        return root
    }


    fun realizarPago(){

        binding.btnRealizarPago.setOnClickListener{
            try{
                var descripcion = binding.etDescripcion.text.toString()
                var cantidad = binding.etCantidad.text.toString()

                val url = "http://localfavas.online/Egresos/InsertEgresos.php"
                val queue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String>{
                            response ->  Toast.makeText(activity, "Pagro realizado", Toast.LENGTH_LONG).show()
                    },Response.ErrorListener { error -> Toast.makeText(activity, "Error $error", Toast.LENGTH_LONG).show() }){
                    override fun getParams(): MutableMap<String, String> {
                        val parametros=HashMap<String, String>()
                        parametros.put("descripcion",descripcion)
                        parametros.put("monto",cantidad)
                        return parametros

                    }
                }
                queue.add(resultadoPost)
            }catch (ex: Exception){
                Toast.makeText(requireContext(), "Error al insertar: ${ex.toString()}", Toast.LENGTH_LONG).show()
            }
           // val navController = Navigation.findNavController(binding.root)
            //navController.navigate(R.id.listaPagosFragment)


        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}