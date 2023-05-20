package com.example.appfavas.dao

import androidx.room.*
import com.example.appfavas.modelos.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserUs(usuario: Usuario):Long

    @Update
    fun actualizarUs(usuario: Usuario): Int

    @Delete
    fun eliminarUs(usuario: Usuario): Int


}