package com.example.appfavas.modelos.Inventario

import android.content.Context


data class Inventario(
    val idInventario: Int,
    val Producto_idProducto: Int,
    val tipoMovimiento: String,
    val cantidad: Int,
    val fecha: String
)
