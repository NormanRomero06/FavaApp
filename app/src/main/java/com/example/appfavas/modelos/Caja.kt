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
data class Caja(
    @PrimaryKey
    val idTransaccion: Int,
    val fechaTransaccion: String?,
    val tipoTransaccion: String?,
    val montoTransaccion: Float?,
    val Venta_idVenta: Int
)