package com.example.appfavas.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["idCategoria"],
            childColumns = ["Categoria_idCategoria"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = DetallesVenta::class,
            parentColumns = ["idDetalleVenta"],
            childColumns = ["DetallesVenta_idDetalleVenta"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("Categoria_idCategoria"), Index("DetallesVenta_idDetalleVenta")]
)
data class Producto(
    @PrimaryKey
    val idProducto: Int,
    val nombre: String?,
    val precio: Float?,
    val descripcion: String?,
    val cantidad: Int?,
    val imagen: ByteArray?,
    val Categoria_idCategoria: Int,
    val DetallesVenta_idDetalleVenta: Int
)