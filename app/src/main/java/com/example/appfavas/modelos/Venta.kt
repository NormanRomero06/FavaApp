package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["Usuario_idUsuario"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("Usuario_idUsuario")]
)
data class Venta(
    @PrimaryKey
    val idVenta: Int,
    val cantidad: Int?,
    val totalVenta: Float?,
    val Usuario_idUsuario: Int
)