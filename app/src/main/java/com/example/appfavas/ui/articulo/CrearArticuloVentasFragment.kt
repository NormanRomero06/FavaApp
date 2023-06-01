package com.example.appfavas.ui.articulo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentCrearArticuloVentasBinding
import com.example.appfavas.modelos.Articulo.ArticuloAdapter
import com.example.appfavas.modelos.Categoria.Categoria
import com.example.appfavas.modelos.InventarioTemp.InventarioTempAdapter
import org.json.JSONException


class CrearArticuloVentasFragment : Fragment() {
    private lateinit var binding: FragmentCrearArticuloVentasBinding
    private lateinit var categorias: MutableList<Categoria>



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCrearArticuloVentasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Lista de categorias
        categorias = mutableListOf()
        obtenerDatosSpinner()
        crearArticulo()
        return root
    }

    fun crearArticulo(){
        binding.btnCrearArt.setOnClickListener {
            try {
                if (validarCampos()){
                    val nombre = binding.etNombreArticulo.text.toString()
                    val descripcion = binding.etDescripcion.text.toString()
                    val precio = binding.etPrecioArticulo.text.toString()
                    val cantMin = 1
                    val selectedCategoryIndex = binding.sCategorA.selectedItemPosition
                    val selectedCategory = categorias[selectedCategoryIndex]
                    val stock = binding.etStockArticulo.text.toString()

                    val url = "http://localfavas.online/Producto/InsertProducto.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String>{ response ->
                            Log.d("InsertarProducto","Respuesta del servidor: $response" )
                            val idProducto = response.toIntOrNull()
                            if (idProducto != null){
                                guardarIdProductoEnSharedPreferences(idProducto)
                                Toast.makeText(
                                    context,
                                    "Producto insertado existosamente",
                                    Toast.LENGTH_LONG
                                ).show()
                                insertarInventario()
                                Navigation.findNavController(binding.root).navigate(R.id.nav_articulos)
                            } else{
                                Toast.makeText(
                                    context,
                                    "Error al obtener ID",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("nombre", nombre)
                            parametros.put("precio", precio)
                            parametros.put("descripcion", descripcion)
                            parametros.put("cantidad", stock)
                            parametros.put("cantidadMinima", cantMin.toString())
                            //parametros.put("imagen", imgen)
                            parametros.put("Categoria_idCategoria", selectedCategory.id.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)
                }
            } catch (ex: Exception){
                Toast.makeText(requireContext(), "Error al insertar: ${ex.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun insertarInventario(){
        val url = "http://localfavas.online/Inventario/ActualizarEntradas.php"
        val queue = Volley.newRequestQueue(activity)
        val resultadoPost = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(
                    context,
                    "Inventario Actualizado",
                    Toast.LENGTH_LONG
                ).show()
                limpiarCampos()
            }, Response.ErrorListener { error ->
                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                val idProducto = obtenerIdProductoDesdeSharedPreferences()
                parametros.put("Producto_idProducto", idProducto.toString())
                parametros.put("tipoMovimiento", "Entrada")
                parametros.put("cantidad", binding.etStockArticulo.text.toString())
                Log.d("Prueba1", "Parametros:$parametros")//Prueba
                return parametros

            }
        }
        queue.add(resultadoPost)
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

    private fun validarCampos(): Boolean {
        var valido = true
        val nombre = binding.etNombreArticulo.text.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val precio = binding.etPrecioArticulo.text.toString()
        val cantidad = binding.etStockArticulo.text.toString()

        if (nombre.isNullOrEmpty()) {
            binding.etNombreArticulo.setError("Por favor rellene este campo")
            valido = false
        }
        if (descripcion.isNullOrEmpty()) {
            binding.etDescripcion.setError("Por favor ponga una descripcion")
            valido = false
        }
        if (precio.isNullOrEmpty()) {
            binding.etPrecioArticulo.setError("Por favor agregue un precio")
            valido = false
        }
        if (cantidad.isNullOrEmpty()) {
            binding.etStockArticulo.setError("Por favor indique la cantidad existente")
            valido = false
        }
        return valido
    }

    private fun obtenerDatosSpinner() {
        val url = "http://localfavas.online/Categoria/ReadCategoria.php"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
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

    private fun guardarIdProductoEnSharedPreferences(idProducto: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("idProducto", idProducto)
        editor.apply()
    }

    private fun obtenerIdProductoDesdeSharedPreferences(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idProducto", 0)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }


}