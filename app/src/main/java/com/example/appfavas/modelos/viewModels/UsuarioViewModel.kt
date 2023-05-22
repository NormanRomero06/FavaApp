package com.example.appfavas.modelos.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appfavas.dao.AppDatabase
import com.example.appfavas.dao.UsuarioDao
import com.example.appfavas.modelos.Usuario.Usuario
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

    //Acá se define un objeto LiveData llamado todos el cual es obtenido medinate la funcion obtenerTodos
    //de usuarioDao, utilizado para observar y obtener la lista de todos los usuarios almacenados
    //en la base de datos en tiempo real

    val todos: LiveData<List<Usuario>> = usuarioDao.obtenerTodos()


}