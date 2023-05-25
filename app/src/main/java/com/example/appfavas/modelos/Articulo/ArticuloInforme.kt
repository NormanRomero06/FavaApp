package com.example.appfavas.modelos.Articulo

data class ArticuloInforme(
    val idProducto: Int,
    val nombre: String,
    val precio: Float,
    val descripcion: String?,
    val cantidad: Int,
    val cantidadMinima: Int,
    //val imagen: String?,
    val categoria: String
)
