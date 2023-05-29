package com.example.appfavas.modelos.InventarioTemp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InventarioTemp")
data class InventarioTemp(
    @PrimaryKey(autoGenerate = false)
    val idProducto: Int,
    val nombre: String,
    val precio: Float,
    val cantidadVenta: Int,
    val precioTotal: Float
)
