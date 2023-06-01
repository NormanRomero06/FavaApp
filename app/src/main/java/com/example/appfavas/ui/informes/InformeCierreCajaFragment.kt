package com.example.appfavas.ui.informes

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformeCierreCajaBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.Articulo
import com.example.appfavas.modelos.Articulo.ArticuloInforme
import com.example.appfavas.modelos.Caja.Cierre
import com.example.appfavas.modelos.Caja.CierreAdapter
import com.itextpdf.text.pdf.PdfWriter
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class InformeCierreCajaFragment : Fragment() {
    private lateinit var binding: FragmentInformeCierreCajaBinding
    private val ciList = ArrayList<Cierre>()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformeCierreCajaBinding.inflate(inflater, container, false)

        val root: View = binding.root
        cargarCierreCaja()


        /*binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }*/
        return root
    }

    private fun cargarCierreCaja() {
        val queue = Volley.newRequestQueue(context)
        val url = "http://localfavas.online/CierreCaja/MostrarCierre.php"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val data = response.getJSONArray("data")
                    ciList.clear()

                    for (i in 0 until data.length()) {
                        val item = data.getJSONObject(i)
                        val fecha = item.getString("fecha")
                        val resultado = item.getString("resultado")

                        println(fecha)
                        println(resultado)

                        val user = Cierre(fecha, resultado)
                        ciList.add(user)
                    }

                    recyclerView = binding.rvCierre
                    val spanCount = 2
                    val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
                    val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
                    val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
                    recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
                    recyclerView?.layoutManager = gridLayoutManager
                    recyclerView?.adapter = CierreAdapter(ciList)
                } catch (e: Exception) {
                    println("Error al procesar la respuesta: " + e.message)
                }
            },
            { error ->
                println("Error: " + error.message)
            }
        )

        queue.add(request)
    }


    /*private fun generarPDF() {
        val filePath = getPDFfilePath()
        val document = Document()
        try {
            PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()

            // Resto del código para generar el PDF

            document.close()

            Toast.makeText(
                requireContext(),
                "PDF generado exitosamente en Descargas",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al generar el PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPDFfilePath(): String {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "Informe de los Artículos.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }*/
}
