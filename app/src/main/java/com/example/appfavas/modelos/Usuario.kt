package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int =0,
    val nombres: String?,
    val apellidos: String?,
    val correo: String?,
    val usuario: String?,
    val contraseña: String?,
    val rol: String?
)