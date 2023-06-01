package com.example.appfavas.modelos.Ventas

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
import com.example.appfavas.databinding.ItemVentasBinding

class VentasAdapter(private val ventasList: ArrayList<Ventas>, private val context: Context) :
    RecyclerView.Adapter<VentasAdapter.VentasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentasViewHolder {
        val userItem =
            ItemVentasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VentasViewHolder(userItem, context)

    }

    override fun getItemCount(): Int {
        return ventasList.size
    }

    override fun onBindViewHolder(holder: VentasViewHolder, position: Int) {
        holder.load(this.ventasList[position])
    }

    class VentasViewHolder(private val binding: ItemVentasBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun load(venta: Ventas) {
            // Aquí establece los valores de los elementos de la vista con los datos de la venta
            binding.tvIdVenta.text = venta.idVenta.toString()
            binding.tvVenta.text = venta.totalVenta.toString()
            binding.tvUsuario.text = venta.usuario

            binding.IvDelete.setOnClickListener {
                try {
                    val url = "http://localfavas.online/Venta/DeleteVenta.php"
                    val queue = Volley.newRequestQueue(this@VentasViewHolder.context)
                    val resultadoPost = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener<String> { response ->
                            Toast.makeText(
                                this@VentasViewHolder.context,
                                "Eliminado existosamente",
                                Toast.LENGTH_LONG
                            ).show()
                            val navController = Navigation.findNavController(binding.root)
                            navController.navigate(R.id.nav_recibos)
                        }, Response.ErrorListener { error ->
                            Toast.makeText(this@VentasViewHolder.context, "Error: $error", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val parametros = HashMap<String, String>()
                            parametros.put("idVenta", binding.tvIdVenta.text.toString())
                            return parametros
                        }
                    }
                    queue.add(resultadoPost)
                } catch (ex: Exception) {
                    Toast.makeText(
                        this@VentasViewHolder.context,
                        "Error al Eliminar: ${ex.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
