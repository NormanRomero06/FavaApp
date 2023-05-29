package com.example.appfavas.modelos.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfavas.dao.InventarioTempDao
import com.example.appfavas.modelos.InventarioTemp.InventarioTemp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventarioTempViewModel(private val inventarioTempDao: InventarioTempDao) : ViewModel() {

    fun insertInventarioTemp(inventarioTemp: InventarioTemp) {
        viewModelScope.launch(Dispatchers.IO) {
            inventarioTempDao.insert(inventarioTemp)
        }
    }

    fun getAllInventarioTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            val inventariosTemp = inventarioTempDao.getAll()
            // Aquí puedes realizar alguna acción con los datos obtenidos, como actualizar la interfaz de usuario
        }
    }
}