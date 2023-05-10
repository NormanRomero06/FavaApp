package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["Egresos_idPagos", "CierreCaja_idCierres"],
    foreignKeys = [
        ForeignKey(
            entity = Egresos::class,
            parentColumns = ["idPagos"],
            childColumns = ["Egresos_idPagos"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = CierreCaja::class,
            parentColumns = ["idCierres"],
            childColumns = ["CierreCaja_idCierres"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("Egresos_idPagos"), Index("CierreCaja_idCierres")]
)
data class Egresos_has_CierreCaja(
    val Egresos_idPagos: Int,
    val CierreCaja_idCierres: Int
)