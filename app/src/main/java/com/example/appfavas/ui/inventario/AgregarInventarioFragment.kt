package com.example.appfavas.ui.inventario

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentAgregarInventarioBinding
import com.example.appfavas.decoration.SpaceItemDecoration


class AgregarInventarioFragment : Fragment() {

    private lateinit var binding: FragmentAgregarInventarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgregarInventarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cargarSpinner()
        entradaInventario()
        return root
    }


    fun entradaInventario() {
        val idProducto = arguments?.getString("idProducto")
        val nombre = arguments?.getString("nombre")
        val id = binding.tvIdProducto.text.toString()
        val descripcion = arguments?.getString("descripcion")
        val precio = arguments?.getString("precio")
        val categoria = arguments?.getString("Categoria_Nombre")
        Log.d(TAG, "Parametros del Item:$categoria")
        var cantidadActual = arguments?.getString("cantidad")?.toInt()
        binding.tvIdProducto.setText(idProducto)
        binding.tvNombreArticulo.text = nombre
        binding.tvIdProducto.setText(idProducto)



        binding.btnActualizar.setOnClickListener {
            if (validarCampos()) {
                try {
                    val id = binding.tvIdProducto.text.toString()
                    val movimiento = binding.spEntSal.selectedItem
                    val cantidad = binding.etCantidad.text.toString()
                    val url = "http://localfavas.online/Inventario/ActualizarEntradas.php"
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Insertado exitosamente",
                                Toast.LENGTH_LONG
                            ).show()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("Producto_idProducto", id)
                            parametros.put("tipoMovimiento", movimiento.toString())
                            parametros.put("cantidad", cantidad)
                            Log.d("Prueba1", "Parametros:$parametros")//Prueba
                            return parametros

                        }
                    }
                    queue.add(resultadoPost)
                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al insertar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                try {

                    val cantidad = binding.etCantidad.text.toString().toInt()
                    val cantMin = 1
                    val link = "http://localfavas.online/Producto/UpdateProducto.php"
                    var resultado: String
                    val queue = Volley.newRequestQueue(activity)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, link,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                context,
                                "Inventario Actualizado",
                                Toast.LENGTH_LONG
                            ).show()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val parametros = HashMap<String, String>()
                            parametros.put("idProducto", binding.tvIdProducto.text.toString())
                            parametros.put("nombre", nombre.toString())
                            parametros.put("descripcion", descripcion.toString())
                            parametros.put("precio", precio.toString())
                            parametros.put("Categoria_idCategoria", categoria.toString())
                            parametros.put("cantidadMinima", cantMin.toString())
                            if (binding.spEntSal.selectedItem == "Entrada") {
                                resultado = (cantidadActual!! + cantidad).toString()
                                Log.d(
                                    TAG,
                                    "SUMA de cantidad:$cantidadActual + $cantidad = $resultado"
                                )
                            } else if (binding.spEntSal.selectedItem == "Salida") {
                                resultado = (cantidadActual!! - cantidad).toString()
                                Log.d(
                                    TAG,
                                    "SUMA de cantidad:$cantidadActual - $cantidad = $resultado"
                                )
                            } else {
                                resultado = ""

                            }
                            cantidadActual = resultado?.toInt()

                            parametros.put("cantidad", resultado)
                            Log.d(TAG, "Parametros:$parametros")
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)
                } catch (ex: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error al insertar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }
    }


    fun cargarSpinner() {
        val spinner: Spinner = binding.spEntSal
        val datos: List<String> = listOf("Entrada", "Salida")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, datos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun validarCampos(): Boolean {
        var valido = true
        val cantidad = binding.etCantidad.text.toString()

        if (cantidad.isNullOrEmpty()) {
            binding.etCantidad.setError("Por favor indique la cantidad")
            valido = false
        }
        return valido
    }


}