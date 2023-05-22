package com.example.appfavas.ui.articulo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentCrearArticuloVentasBinding



class CrearArticuloVentasFragment : Fragment() {
    private lateinit var binding: FragmentCrearArticuloVentasBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCrearArticuloVentasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        crearArticulo()
        return root
    }
    fun crearArticulo(){
        binding.btnCrearArt.setOnClickListener {
            try {
                val minimo = 1
                val nombre = binding.etNombreArticulo.text.toString()
                val descripcion = binding.etDescripcion.text.toString()
                val precio = binding.etPrecioArticulo.text.toString()
                val cantMin = minimo.toString().toInt()
                val cat = binding.sCategorA.selectedItem
                val stock = binding.etStockArticulo.text.toString()

                val url = "http://localfavas.online/Producto/InsertProducto.php"
                val queue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String>{ response ->
                        Toast.makeText(
                            getActivity(),
                            "Insertado existosamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(getActivity(), "Error: $error", Toast.LENGTH_LONG).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val parametros = HashMap<String, String>()
                        parametros.put("nombre", nombre)
                        parametros.put("precio", precio)
                        parametros.put("descripcion", descripcion)
                        parametros.put("cantidad", stock)
                        parametros.put("cantidadMinima", cantMin.toString())
                        //parametros.put("imagen", imgen)
                        return parametros
                    }
                }
                queue.add(resultadoPost)
                limpiarCampos()
            } catch (ex: Exception){
                Toast.makeText(requireContext(), "Error al insertar: ${ex.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun limpiarCampos() {
        with(binding) {
            btnCrearArt.setOnClickListener {
                etNombreArticulo.setText("")
                etDescripcion.setText("")
                etPrecioArticulo.setText("")
                etStockArticulo.setText("")

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}