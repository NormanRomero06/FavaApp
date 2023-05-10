package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Venta::class,
            parentColumns = ["idVenta"],
            childColumns = ["Venta_idVenta"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("Venta_idVenta")]
)
data class DetallesVenta(
    @PrimaryKey
    val idDetalleVenta: Int,
    val cantidadVendida: Int?,
    val precioUnitario: Float?,
    val Venta_idVenta: Int
)