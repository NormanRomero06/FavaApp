package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categoria(
    @PrimaryKey
    val idCategoria: Int,
    val nombre: String?,
    val imagen: ByteArray?
)