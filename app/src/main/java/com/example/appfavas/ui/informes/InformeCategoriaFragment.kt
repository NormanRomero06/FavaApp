package com.example.appfavas.ui.informes

import androidx.fragment.app.Fragment

class InformeCategoriaFragment:Fragment() {
    /* override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       //binding =

      val root: View = binding.root

      binding.btnGenerarPDF.setOnClickListener {
       generarPDF()
       }

 //  }*/
    /*    private fun generarPDF() {
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
            val url = ""

            val PrincipalFont = Font(baseFont, 16f, Font.BOLD, BaseColor(194, 246, 196))
            val dataFont = Font(baseFont, 14f, Font.NORMAL)
            // Crear la solicitud JSON utilizando Volley
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Procesar la respuesta JSON
                    val inventario = response.getJSONArray("data")

                    // Recorrer los resultados y agregarlos al PDF
                    for (i in 0 until inventario.length()) {
                        val categoria = categoria.getJSONObject(i)
                        // Agregar la imagen del logo
                        val logoBitmap = BitmapFactory.decodeResource(resources, R.drawable.favaslog)
                        val stream = ByteArrayOutputStream()
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val logoImage = Image.getInstance(stream.toByteArray())
                        logoImage.alignment = Element.ALIGN_LEFT
                        logoImage.scaleAbsolute(100f, 100f)
                        document.add(logoImage)


                        val idCategoria = categoria.getInt("idCategoria")
                        val nombre= categoria.getString("nombre")



                        val chunkId = Chunk("ID del Producto: $idProducto", dataFont)
                        val chunkNombre = Chunk("Nombre del Producto: $nombre", dataFont)

                        document.add(Paragraph(""))

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
        val fileName = "Informe Categoría.pdf"
        val filePath = File(directory, fileName)
        return filePath.absolutePath
    }*/
}