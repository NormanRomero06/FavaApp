package com.example.appfavas.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp

@Dao
interface InventarioTempDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(inventarioTemp: InventarioTemp)

    @Query("SELECT * FROM InventarioTemp")
    fun getAll(): List<InventarioTemp>
}