package com.example.appfavas.dao

import android.content.Context

class PreferShared(val context: Context) {
    val SHARED_NAME = "BfFavas"
    val SHARED_IDprod = "ProductoID"
    val SHARED_NOMBRE = "Nombre"
    val SHARED_CANTIDAD = "Cantidad"
    val SHARED_PRECIO = "Precio"
    val SHARED_TOTAL = "Total"
    val SHARED_CAMBIO = "Cambio"


    //Variable de acceso al BD
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveIdPro(IdPro: Int) {
        storage.edit().putInt(SHARED_IDprod,IdPro).apply()
    }

    fun saveCambio(Cambio: Float) {
        storage.edit().putFloat(SHARED_CAMBIO,Cambio).apply()
    }
    fun saveNombre(Nombre: String) {
        storage.edit().putString(SHARED_NOMBRE, Nombre).apply()
    }
    fun saveCantidad(Cantidad: Int) {
        storage.edit().putInt(SHARED_CANTIDAD, Cantidad).apply()
    }
    fun savePrecio(Precio: Float) {
        storage.edit().putFloat(SHARED_PRECIO, Precio).apply()
    }
    fun saveTotal(Total: Float) {
        storage.edit().putFloat(SHARED_TOTAL, Total).apply()
    }


    //Metodos de Retorno
    fun getID(): Int {
        return storage.getInt(SHARED_IDprod, 0)!!
    }
    fun getCambio(): Float {
        return storage.getFloat(SHARED_CAMBIO, 0.0f)!!
    }
    fun getNombre(): String {
        return storage.getString(SHARED_NOMBRE, "")!!
    }
    fun getCantidad(): Int {
        return storage.getInt(SHARED_CANTIDAD, 0)!!
    }
    fun getPrecio(): Float   {
        return storage.getFloat(SHARED_PRECIO, 0.0f)!!
    }
    fun getTotal(): Float {
        return storage.getFloat(SHARED_TOTAL, 0.0f)!!
    }
    fun wipe() {
        storage.edit().clear().apply()
    }
}