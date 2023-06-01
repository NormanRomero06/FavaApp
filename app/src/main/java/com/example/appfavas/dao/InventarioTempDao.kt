package com.example.appfavas.dao

import androidx.room.*
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp

@Dao
interface InventarioTempDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(inventarioTemp: InventarioTemp)

    @Query("SELECT * FROM InventarioTemp")
    fun getAll(): List<InventarioTemp>

    @Query("DELETE FROM InventarioTemp")
    fun deleteAll()

    @Delete
    fun delete(inventarioTemp: InventarioTemp)
}