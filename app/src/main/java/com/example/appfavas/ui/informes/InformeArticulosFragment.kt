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
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appfavas.R
import com.example.appfavas.databinding.FragmentInformeArticulosBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Articulo.ArticuloInforme
import com.example.appfavas.modelos.Articulo.ArticuloInformeAdapter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class InformeArticulosFragment : Fragment() {
    private lateinit var binding: FragmentInformeArticulosBinding
    val artList = arrayListOf<ArticuloInforme>()
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformeArticulosBinding.inflate(inflater, container, false)

        val root: View = binding.root
        cargarProductos()

        binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }
        return root
    }

    fun cargarProductos() {

        val uri = "http://localfavas.online/Producto/ReadData.php"

        recyclerView = binding.rvArticulos
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            artList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = ArticuloInforme(
                    jsonObj.getInt("idProducto"),
                    jsonObj.getString("nombre"),
                    jsonObj.getDouble("precio").toFloat(),
                    jsonObj.getString("descripcion"),
                    jsonObj.getInt("cantidad"),
                    jsonObj.getInt("cantidadMinima"),
                    //jsonObj.getString("imagen")
                    jsonObj.getString("Categoria_Nombre")

                )
                artList.add(user)
            }
            println(artList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = ArticuloInformeAdapter(artList)


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
            val titulo = "Informe de los Artículos"
            val titleParagraph = Paragraph(titulo, titleFont).apply {
                alignment = Element.ALIGN_CENTER
                spacingAfter = 10f
            }
            document.add(titleParagraph)

            // Realizar la solicitud Volley para obtener los datos
            val url = "http://localfavas.online/Producto/ReadData.php"

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
                        val idProducto = inventario.getInt("idProducto")
                        val nombre = inventario.getString("nombre")
                        val precio = inventario.getDouble("precio")
                        val descripcion = inventario.getString("descripcion")
                        val cantidad = inventario.getInt("cantidad")
                        val cantidadMinima = inventario.getInt("cantidadMinima")
                        val Categoria_Nombre = inventario.getString("Categoria_Nombre")


                        val chunkCategoria =
                            Chunk("Categoría del Producto: $Categoria_Nombre", PrincipalFont)
                        val chunkId = Chunk("ID del Producto: $idProducto", dataFont)
                        val chunkNombre = Chunk("Nombre del Producto: $nombre", dataFont)
                        val chunkPrecio = Chunk("Precio del Producto: $precio", dataFont)
                        val chunkDecripcion =
                            Chunk("Decripción del Producto: $descripcion", dataFont)
                        val chunkCantidad = Chunk("Cantidad de Producto: $cantidad", dataFont)
                        val chunkCantMin =
                            Chunk("Cantidad Mínima de Producto: $cantidadMinima", dataFont)

                        document.add(Paragraph(""))
                        val paragraphCategoria = Paragraph(chunkCategoria).apply {
                            spacingAfter = 20f
                            alignment = Element.ALIGN_MIDDLE
                        }
                        document.add(Paragraph(paragraphCategoria))
                        val paragraphId = Paragraph(chunkId).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphId))
                        val paragraphNombre = Paragraph(chunkNombre).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphNombre))

                        val paragraphPrecio = Paragraph(chunkPrecio).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphPrecio))
                        val paragraphDescripcion = Paragraph(chunkDecripcion).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphDescripcion))
                        val paragraphCantidad = Paragraph(chunkCantidad).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphCantidad))
                        val paragraphCantMin = Paragraph(chunkCantMin).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphCantMin))
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
        val fileName = "Informe de los Artículos.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }
}