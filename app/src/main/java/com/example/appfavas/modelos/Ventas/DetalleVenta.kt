package com.example.appfavas.modelos.Ventas

data class DetalleVenta(
    var idDetalleVente: Int,
    var cantidadVendida: Int,
    var precioUnitario: Float,
    var producto_IdProducto: Int,

)
