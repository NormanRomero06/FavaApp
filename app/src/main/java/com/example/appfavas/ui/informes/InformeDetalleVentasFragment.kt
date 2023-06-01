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
import com.example.appfavas.databinding.FragmentInformeDetalleVentasBinding
import com.example.appfavas.decoration.SpaceItemDecoration
import com.example.appfavas.modelos.Ventas.DetalleVentaInforme
import com.example.appfavas.modelos.Ventas.DetalleVentaInformeAdapter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class InformeDetalleVentasFragment : Fragment() {

    private lateinit var binding: FragmentInformeDetalleVentasBinding
    val detalleVentaList = arrayListOf<DetalleVentaInforme>()
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformeDetalleVentasBinding.inflate(inflater,container, false)

        val root: View = binding.root
        cargarProductos()

        binding.btnGenerarPDF.setOnClickListener {
            generarPDF()
        }
        return root
    }

    private fun cargarProductos() {
        val uri = "http://localfavas.online/DetallesVentas/ReadDetalleVentas.php"

        recyclerView = binding.rvInformeVentas
        val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET, uri, null, { res ->
            val jsonArray = res.getJSONArray("data")

            //Limpia la lista para evitar items duplicados
            detalleVentaList.clear()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val user = DetalleVentaInforme(
                    jsonObj.getString("Producto"),
                    jsonObj.getInt("Cantidad_Vendida"),
                    jsonObj.getDouble("Precio_Unitario").toFloat(),
                    jsonObj.getInt("Total_Detalle_Venta"),
                    jsonObj.getInt("Codigo_de_Venta"),
                    jsonObj.getInt("Total_Venta"),
                    jsonObj.getString("Usuario")
                )
                detalleVentaList.add(user)
            }
            println(detalleVentaList.toString())

            val spanCount = 2 // Número de columnas en la cuadrícula
            val spaceHorizontal = resources.getDimensionPixelSize(R.dimen.item_space_horizontal)
            val spaceVertical = resources.getDimensionPixelSize(R.dimen.item_space_vertical)
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            val space =
                resources.getDimensionPixelSize(R.dimen.item_space) // Define el tamaño del espacio
            recyclerView?.addItemDecoration(SpaceItemDecoration(spaceHorizontal, spaceVertical))
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = DetalleVentaInformeAdapter(detalleVentaList)


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
            val titulo = "Informe de Detalle Ventas"
            val titleParagraph = Paragraph(titulo, titleFont).apply {
                alignment = Element.ALIGN_CENTER
                spacingAfter = 10f
            }
            document.add(titleParagraph)

            // Realizar la solicitud Volley para obtener los datos
            val url = "http://localfavas.online/DetallesVentas/ReadDetalleVentas.php"

            val PrincipalFont = Font(baseFont, 16f, Font.BOLD, BaseColor(194, 246, 196))
            val dataFont = Font(baseFont, 14f, Font.NORMAL)
            // Crear la solicitud JSON utilizando Volley
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Procesar la respuesta JSON
                    val detalleVenta= response.getJSONArray("data")

                    // Recorrer los resultados y agregarlos al PDF
                    for (i in 0 until detalleVenta.length()) {
                        val detalleVenta = detalleVenta.getJSONObject(i)
                        // Agregar la imagen del logo
                        val logoBitmap = BitmapFactory.decodeResource(resources, R.drawable.favaslog)
                        val stream = ByteArrayOutputStream()
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val logoImage = Image.getInstance(stream.toByteArray())
                        logoImage.alignment = Element.ALIGN_LEFT
                        logoImage.scaleAbsolute(100f, 100f)
                        document.add(logoImage)

                        val producto = detalleVenta.getString("Producto")
                        val cantidadVendida = detalleVenta.getInt("Cantidad_Vendida")
                        val precioUnitario = detalleVenta.getDouble("Precio_Unitario").toFloat()
                        val totalDetalleVenta = detalleVenta.getInt("Total_Detalle_Venta")
                        val codigoVenta = detalleVenta.getInt("Codigo_de_Venta")
                        val totalVenta = detalleVenta.getInt("Total_Venta")
                        val usuario = detalleVenta.getString("Usuario")

                        val chunkProducto =
                            Chunk("Producto: $producto", dataFont)
                        val chunkCantidadVendida= Chunk("Cantidad Vendida: $cantidadVendida", dataFont)
                        val chunkPrecioUnitario = Chunk("Precio Unitario: $precioUnitario", dataFont)
                        val chunckTotalDetalleVenta = Chunk("Total Detalle Venta: $totalDetalleVenta", dataFont)
                        val chunckCodigoVenta = Chunk("Código de Venta: $codigoVenta",dataFont)
                        val chunkTotalVenta = Chunk("Total Venta: $totalVenta", dataFont)
                        val chunkUsuario = Chunk("Usuario: $usuario", dataFont)

                        document.add(Paragraph(""))

                        val paragraphProducto= Paragraph(chunkProducto).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphProducto))

                        val paragraphCantidadVendida = Paragraph(chunkCantidadVendida).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphCantidadVendida))

                        val paragraphPrecioUnitario = Paragraph(chunkPrecioUnitario).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphPrecioUnitario))

                        val paragraphDetalleVenta= Paragraph(chunckTotalDetalleVenta).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphDetalleVenta))

                        val paragraphCodigoVenta= Paragraph(chunckCodigoVenta).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphCodigoVenta))

                        val paragraphTotalVenta= Paragraph(chunkTotalVenta).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphTotalVenta))

                        val paragraphUsuario= Paragraph(chunkUsuario).apply {
                            spacingAfter = 10f
                            alignment = Element.ALIGN_LEFT
                        }
                        document.add(Paragraph(paragraphUsuario))

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
        val fileName = "Informe Detalle Ventas.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }
}