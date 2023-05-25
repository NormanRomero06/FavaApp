package com.example.appfavas.modelos.Articulo

data class Articulo(
    var idProducto: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Float,
    var cantidad: Int,
    //var cantidaMinima: Int,
    //var imagen: Bitmap,
    var Categoria_idCategoria: Int
)
