package com.example.appfavas.modelos.Ventas

data class DetalleVentaInforme(
    val Producto:String,
    val Cantidad_Vendida:Int,
    val Precio_Unitario:Float,
    val Total_Detalle_Venta:Int,
    val Codigo_de_Venta:Int,
    val Total_Venta: Int,
    val Usuario:String
)
