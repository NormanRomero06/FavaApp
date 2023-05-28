package com.example.appfavas.ui.articulo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.databinding.FragmentEditarEliminarArticulosVentasBinding
import com.example.appfavas.modelos.Categoria.Categoria
import org.json.JSONException


class Editar_EliminarArticulosVentasFragment : Fragment() {
    private lateinit var binding: FragmentEditarEliminarArticulosVentasBinding
    private lateinit var categorias: MutableList<Categoria>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditarEliminarArticulosVentasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        categorias = mutableListOf()
        obtenerDatosSpinner()
        editarProducto()
        eliminarCategoria()
        btnLimpiar()
        return root
    }

    fun editarProducto() {
        val idProducto = arguments?.getString("idProducto")
        val nombre = arguments?.getString("nombre")
        val descripcion = arguments?.getString("descripcion")
        val precio = arguments?.getString("precio")
        val cantidad = arguments?.getString("cantidad")
        //val categoria = arguments?.getInt("Categoria_Nombre")
        binding.etId.setText(idProducto)
        binding.etNombreArticulo.setText(nombre)
        binding.etDescripcion.setText(descripcion)
        binding.etPrecioArticulo.setText(precio)
        binding.etStockArticulo.setText(cantidad)
        /*if (categoria != null) {
            binding.sCategorA.setSelection(categoria)
        }*/

        with(binding) {
            btnEditarArt.setOnClickListener {
                try {
                    if (validarCampos()){
                    val cantMin = 1
                    val selectedCategoryIndex = binding.sCategorA.selectedItemPosition
                    val selectedCategory = categorias[selectedCategoryIndex]

                    val url = "http://localfavas.online/Producto/UpdateProducto.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Modificado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiarCampos()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("idProducto", binding.etId.text.toString())
                            parametros.put("nombre", binding.etNombreArticulo.text.toString())
                            parametros.put("precio", binding.etPrecioArticulo.text.toString())
                            parametros.put("descripcion", binding.etDescripcion.text.toString())
                            parametros.put("cantidad", binding.etStockArticulo.text.toString())
                            parametros.put("cantidadMinima", cantMin.toString())
                            //parametros.put("imagen", imgen)
                            parametros.put("Categoria_idCategoria", selectedCategory.id.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)
                    }
                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al editar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }

    }

    private fun obtenerDatosSpinner() {
        val url = "http://localfavas.online/Categoria/ReadCategoria.php"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                categorias.clear()

                try {
                    val dataArray = response.getJSONArray("data")

                    for (i in 0 until dataArray.length()) {
                        val categoriaJson = dataArray.getJSONObject(i)
                        val id = categoriaJson.getInt("idCategoria")
                        val nombre = categoriaJson.getString("nombre")
                        //val imagen = categoriaJson.getString("imagen")
                        val categoria = Categoria(id, nombre)
                        categorias.add(categoria)
                    }

                    // Crear el ArrayAdapter con la lista de nombres de categorías
                    val nombresCategorias = categorias.map { it.nombre }
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        nombresCategorias
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.sCategorA.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(request)
    }

    fun eliminarCategoria() {
        with(binding) {
            val idProducto = arguments?.getString("idProducto")
            binding.etId.setText(idProducto)
            btnEliminarArt.setOnClickListener {
                try {
                    val url = "http://localfavas.online/Producto/DeleteProducto.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Eliminado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiarCampos()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val parametros = HashMap<String, String>()
                            parametros.put("idProducto", binding.etId.text.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)


                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al Eliminar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun btnLimpiar() {
        binding.btnLimpiarArt.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun validarCampos():Boolean{
        var valido = true
        val nombre = binding.etNombreArticulo.text.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val precio = binding.etPrecioArticulo.text.toString()
        val cantidad = binding.etStockArticulo.text.toString()

        if(nombre.isNullOrEmpty()){
            binding.etNombreArticulo.setError("Por favor rellene este campo")
            valido= false
        }
        if (descripcion.isNullOrEmpty()){
            binding.etDescripcion.setError("Por favor ponga una descripcion")
            valido= false
        }
        if (precio.isNullOrEmpty()){
            binding.etPrecioArticulo.setError("Por favor agregue un precio")
            valido= false
        }
        if (cantidad.isNullOrEmpty()){
            binding.etStockArticulo.setError("Por favor indique la cantidad existente")
            valido= false
        }
        return valido
    }

    private fun limpiarCampos() {
        with(binding) {
            etNombreArticulo.text.clear()
            etDescripcion.text.clear()
            etPrecioArticulo.text.clear()
            etStockArticulo.text.clear()
            sCategorA.setSelection(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}