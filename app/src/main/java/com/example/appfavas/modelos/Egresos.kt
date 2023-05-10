package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Egresos(
    @PrimaryKey
    val idPagos: Int,
    val descripcion: String?,
    val monto: Float?,
    val fechaHora: String?
)