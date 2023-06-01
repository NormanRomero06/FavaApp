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
import com.example.appfavas.databinding.FragmentInformePagosBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Pago.Pago
import com.example.appfavas.modelos.Pago.PagoAdapter
import com.example.appfavas.modelos.Pago.PagoInformeAdapter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class InformePagosFragment : Fragment() {
    private lateinit var binding:FragmentInformePagosBinding
    val pagoList = arrayListOf<Pago>()
    var recyclerView : RecyclerView? = null
    private var adapter: PagoAdapter? = null

    override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         binding = FragmentInformePagosBinding.inflate(inflater, container, false)

        val root: View = binding.root
        cargarPagos()

        binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }

        return root

     }

    fun cargarPagos(){
        val uri = "http://localfavas.online/Egresos/ReadEgresos.php"
        recyclerView = binding.rvInformePagos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            pagoList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = Pago(
                    jsonObj.getInt("idPagos"),
                    jsonObj.getString("descripcion"),
                    jsonObj.getString("monto").toFloat(),
                    jsonObj.getString("fechaHora")
                )
                pagoList.add(user)
            }
            println(pagoList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView = binding.rvInformePagos
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))

            adapter = PagoAdapter(pagoList)
            recyclerView?.adapter = adapter

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
            val titulo = "Informe Pagos"
            val titleParagraph = Paragraph(titulo, titleFont).apply {
                alignment = Element.ALIGN_CENTER
                spacingAfter = 10f
            }
            document.add(titleParagraph)

            // Realizar la solicitud Volley para obtener los datos
            val url = "http://localfavas.online/Egresos/ReadEgresos.php"

            val PrincipalFont = Font(baseFont, 16f, Font.BOLD, BaseColor(194, 246, 196))
            val dataFont = Font(baseFont, 14f, Font.NORMAL)
            // Crear la solicitud JSON utilizando Volley
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Procesar la respuesta JSON
                    val pagos = response.getJSONArray("data")

                    // Recorrer los resultados y agregarlos al PDF
                    for (i in 0 until pagos.length()) {
                        val pagos = pagos.getJSONObject(i)
                        // Agregar la imagen del logo
                        val logoBitmap = BitmapFactory.decodeResource(resources, R.drawable.favaslog)
                        val stream = ByteArrayOutputStream()
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val logoImage = Image.getInstance(stream.toByteArray())
                        logoImage.alignment = Element.ALIGN_LEFT
                        logoImage.scaleAbsolute(100f, 100f)
                        document.add(logoImage)

                        val idPagos = pagos.getInt("idPagos")
                        val descripcion = pagos.getString("descripcion")
                        val monto = pagos.getDouble("monto")
                        val fechaHora = pagos.getString("fechaHora")


                        val chunkId =
                            Chunk("Número de pago: $idPagos", dataFont)
                        val chunkDescripcion= Chunk("Descripción del pago: $descripcion", dataFont)
                        val chunkMonto = Chunk("Monto: $monto", dataFont)
                        val chunckFechaHora = Chunk("Fecha y Hora del Pago: $fechaHora", dataFont)

                        document.add(Paragraph(""))

                        val paragraphId = Paragraph(chunkId).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphId))

                        val paragraphDescripcion = Paragraph(chunkDescripcion).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphDescripcion))

                        val paragraphMonto = Paragraph(chunkMonto).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphMonto))

                        val paragraphFechaHora = Paragraph(chunckFechaHora).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphFechaHora))

                        document.add(Paragraph("----------------------------------------------------------------------------", PrincipalFont))
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
        val fileName = "Informe Pago.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }
}