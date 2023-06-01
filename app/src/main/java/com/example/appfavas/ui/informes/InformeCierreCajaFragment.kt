package com.example.appfavas.ui.informes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformeCierreCajaBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Caja.Cierre
import com.example.appfavas.modelos.Caja.CierreAdapter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
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

        binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }
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
                    val spaceHorizontal =
                        resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
                    val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
                    val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
                    recyclerView?.addItemDecoration(
                        SpaceItemDecoration(
                            spaceHorizontal,
                            spaceVertical
                        )
                    )
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


    private fun generarPDF() {
        val filePath = getPDFfilePath()
        val document = Document()

        try {
            PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()
            val fontPath = "res/font/crimson_text_regular.ttf"
            val baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)


            // Establecer la fuente y el tamaño para el título
            val titleFont = Font(baseFont, 18f, Font.BOLD)

            // Agregar título centrado en la parte superior del PDF
            val titulo = "Informe Cierre de Caja"
            val titleParagraph = Paragraph(titulo, titleFont).apply {
                alignment = Element.ALIGN_CENTER
                spacingAfter = 10f
            }
            document.add(titleParagraph)

            // Realizar la solicitud Volley para obtener los datos
            val url = "http://localfavas.online/CierreCaja/MostrarCierre.php"

            val PrincipalFont = Font(baseFont, 16f, Font.BOLD, BaseColor(194, 246, 196))
            val dataFont = Font(baseFont, 14f, Font.NORMAL)
            // Crear la solicitud JSON utilizando Volley
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Procesar la respuesta JSON
                    val cierreCaja = response.getJSONArray("data")

                    // Recorrer los resultados y agregarlos al PDF
                    for (i in 0 until cierreCaja.length()) {
                        val cierreCaja = cierreCaja.getJSONObject(i)
                        // Agregar la imagen del logo
                        val logoBitmap =
                            BitmapFactory.decodeResource(resources, R.drawable.favaslog)
                        val stream = ByteArrayOutputStream()
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val logoImage = Image.getInstance(stream.toByteArray())
                        logoImage.alignment = Element.ALIGN_LEFT
                        logoImage.scaleAbsolute(100f, 100f)
                        document.add(logoImage)

                        val fechaHora = cierreCaja.getString("fecha")
                        val resultado = cierreCaja.getString("resultado")

                        val chunkfechaHora =
                            Chunk("Fecha y Hora: $fechaHora", dataFont)
                        val chunkResultado = Chunk("Resultado: $resultado", dataFont)

                        document.add(Paragraph(""))

                        val paragraphFechaHora = Paragraph(chunkfechaHora).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphFechaHora))

                        val paragraphResultado = Paragraph(chunkResultado).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphResultado))

                        document.add(
                            Paragraph(
                                "----------------------------------------------------------------------------",
                                PrincipalFont
                            )
                        )
                    }

                    // Cerrar el documento PDF
                    document.close()

                    // Notificar al usuario que el PDF se generó correctamente
                    Toast.makeText(
                        requireContext(),
                        "PDF generado exitosamente en descargas",
                        Toast.LENGTH_SHORT
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
            Toast.makeText(requireContext(), "Error al generar el PDF", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getPDFfilePath(): String {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "Informe Cierre Caja.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }
}
