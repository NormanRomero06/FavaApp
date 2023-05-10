package com.example.appfavas.modelos.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.dao.UsuarioDao
import com.example.appfavas.modelos.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        usuarioDao = database.usuarioDao()
    }

    suspend fun inserUs(usuario: Usuario) = withContext(Dispatchers.IO){
        usuarioDao.inserUs(usuario)
    }
    suspend fun actualizarUs(usuario: Usuario) = withContext(Dispatchers.IO){
        usuarioDao.actualizarUs(usuario)
    }
    suspend fun eliminarUs(usuario: Usuario) = withContext(Dispatchers.IO){
        usuarioDao.eliminarUs(usuario)
    }


}