package com.example.appfavas.modelos.Inventario

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemInventarioBinding

class InventarioAdapter(
    private val invList: ArrayList<Inventario>,
    private val context: Context
) : RecyclerView.Adapter<InventarioAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemInventarioBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun load(item: Inventario, context: Context) {
            with(binding) {
                tvidInventario.text = item.idInventario.toString()
                tvNombreProducto.text = item.Producto_idProducto.toString()
                tvtipoMovimiento.text = item.tipoMovimiento
                tvExistenciaProducto.text = item.cantidad.toString()
                tvFecha.text = item.fecha

                IvDelete.setOnClickListener {
                    try {
                        val url = "http://localfavas.online/Inventario/DeleteInventario.php"
                        val queue = Volley.newRequestQueue(this@ViewHolder.context)
                        val resultadoPost = object : StringRequest(
                            Request.Method.POST, url,
                            Response.Listener<String> { response ->
                                Toast.makeText(
                                    this@ViewHolder.context,
                                    "Eliminado existosamente",
                                    Toast.LENGTH_LONG
                                ).show()
                                val navController = Navigation.findNavController(binding.root)
                                navController.navigate(R.id.historialInventarioFragment)
                            }, Response.ErrorListener { error ->
                                Toast.makeText(this@ViewHolder.context, "Error: $error", Toast.LENGTH_LONG).show()
                            }) {
                            override fun getParams(): MutableMap<String, String>? {
                                val parametros = HashMap<String, String>()
                                parametros.put("idInventario", binding.tvidInventario.text.toString())
                                return parametros
                            }
                        }
                        queue.add(resultadoPost)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            this@ViewHolder.context,
                            "Error al Eliminar: ${ex.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = ItemInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem, context)
    }


    override fun getItemCount(): Int {
        return invList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inventario = invList[position]
        holder.load(inventario, context)

    }

    fun filter(text: String) {
        val searchText = text.toLowerCase()
        val filteredList = if (searchText.isEmpty()) {
            invList // Mostrar todos los elementos si no hay texto de búsqueda
        } else {
            val tempList = ArrayList<Inventario>()
            for (producto in invList) {
                if (producto.tipoMovimiento.toLowerCase().contains(searchText)) {
                    tempList.add(producto)
                }
            }
            tempList // Mostrar solo los elementos que coinciden con el texto de búsqueda
        }
        updateList(filteredList)
    }

    private fun updateList(list: List<Inventario>) {
        invList.clear()
        invList.addAll(list)
        notifyDataSetChanged()
    }
}


