package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Caja::class,
            parentColumns = ["idTransaccion"],
            childColumns = ["Caja_idTransaccion"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Egresos::class,
            parentColumns = ["idPagos"],
            childColumns = ["Egresos_idPagos"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("Caja_idTransaccion"), Index("Egresos_idPagos")]
)
data class CierreCaja(
    @PrimaryKey
    val idCierres: Int,
    val fechaHora: String?,
    val totalEfectivoCaja: Float?,
    val Caja_idTransaccion: Int,
    val Egresos_idPagos: Int
)