package com.example.appfavas.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appfavas.modelos.Usuario.Usuario

@Dao
interface UsuarioDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserUs(usuario: Usuario):Long

    @Update
    fun actualizarUs(usuario: Usuario): Int

    @Delete
    fun eliminarUs(usuario: Usuario): Int

    //Acá especificamos una consulta personalizada para obtener los registros de la tabla
    //y devuelve un LiveData
    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): LiveData<List<Usuario>>

}