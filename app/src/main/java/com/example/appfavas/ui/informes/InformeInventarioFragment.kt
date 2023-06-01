package com.example.appfavas.ui.informes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformeInventarioBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Inventario.InventarioInforme
import com.example.appfavas.modelos.Inventario.InventarioInformeAdapter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class InformeInventarioFragment : Fragment() {

    private lateinit var binding: FragmentInformeInventarioBinding
    val infoInvList = arrayListOf<InventarioInforme>()
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentInformeInventarioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cargarInventario()
        binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }
        return root
    }

    private fun cargarInventario() {
        val uri = "http://localfavas.online/Inventario/InformeInventario.php"

        recyclerView = binding.rvArticulos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            infoInvList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = InventarioInforme(
                    jsonObj.getString("Producto"),
                    jsonObj.getString("Movimiento"),
                    jsonObj.getInt("cantidad"),
                    jsonObj.getString("fecha")
                )
                infoInvList.add(user)
            }
            println(infoInvList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = InventarioInformeAdapter(infoInvList)


        }, {

        })

        reqQueue.add(request)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

    private fun generarPDF() {
        val filePath = getPDFfilePath()
        // Crear el documento PDF
        val document = Document()
        try {
            PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()

            val fontPath = "res/font/crimson_text_regular.ttf"
            val baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)


            // Establecer la fuente y el tamaño para el título
            val titleFont = Font(baseFont, 18f, Font.BOLD)

            // Agregar título centrado en la parte superior del PDF
            val titulo = "Informe del Inventario"
            val titleParagraph = Paragraph(titulo, titleFont).apply {
                alignment = Element.ALIGN_CENTER
                spacingAfter = 10f
            }
            document.add(titleParagraph)

            // Realizar la solicitud Volley para obtener los datos
            val url = "http://localfavas.online/Inventario/InformeInventario.php"

            val PrincipalFont = Font(baseFont, 16f, Font.BOLD, BaseColor(194, 246, 196))
            val dataFont = Font(baseFont, 14f, Font.NORMAL)
            // Crear la solicitud JSON utilizando Volley
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Procesar la respuesta JSON
                    val articulo= response.getJSONArray("data")


                    // Recorrer los resultados y agregarlos al PDF
                    for (i in 0 until articulo.length()) {
                        val inventario = articulo.getJSONObject(i)
                        // Agregar la imagen del logo
                        val logoBitmap = BitmapFactory.decodeResource(resources, R.drawable.favaslog)
                        val stream = ByteArrayOutputStream()
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val logoImage = Image.getInstance(stream.toByteArray())
                        logoImage.alignment = Element.ALIGN_LEFT
                        logoImage.scaleAbsolute(100f, 100f)
                        document.add(logoImage)
                        val nombreProd = inventario.getString("Producto")
                        val movimiento = inventario.getString("Movimiento")
                        val cantidad = inventario.getInt("cantidad")
                        val fecha = inventario.getString("fecha")

                        val chunkNombre = Chunk("Nombre del Producto: $nombreProd", dataFont)
                        val chunkMovimiento = Chunk("Movimiento: $movimiento", dataFont)
                        val chunkCantidad = Chunk("Cantidad de Producto: $cantidad", dataFont)
                        val chunkFecha =
                            Chunk("Fecha" +
                                    ": $fecha", dataFont)

                        document.add(Paragraph(""))

                        val paragraphNombre = Paragraph(chunkNombre).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphNombre))

                        val paragraphMovimiento = Paragraph(chunkMovimiento).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphMovimiento))

                        val paragraphCantidad = Paragraph(chunkCantidad).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphCantidad))

                        val paragraphFecha = Paragraph(chunkFecha).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphFecha))
                        document.add(Paragraph("----------------------------------------------------------------------------", PrincipalFont))
                    }

                    // Cerrar el documento PDF
                    document.close()

                    // Notificar al usuario que el PDF se generó correctamente
                    Toast.makeText(
                        requireContext(),
                        "PDF generado exitosamente en descargas",
                        Toast.LENGTH_LONG
                    ).show()
                },
                { error ->
                    // Manejar el error de la solicitud
                    error.printStackTrace()
                    // Manejar cualquier error de conexión o generación de PDF
                }
            )

            // Agregar la solicitud a la cola de solicitudes de Volley
            Volley.newRequestQueue(requireContext()).add(request)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al generar el PDF", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPDFfilePath(): String {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "Informe del Inventarios.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }

}