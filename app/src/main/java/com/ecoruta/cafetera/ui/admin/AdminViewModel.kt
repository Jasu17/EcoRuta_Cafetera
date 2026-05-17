package com.ecoruta.cafetera.ui.admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ecoruta.cafetera.data.local.AppDatabase
import com.ecoruta.cafetera.data.local.entity.UsuarioEntity
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).usuarioDao()
    val usuarios: LiveData<List<UsuarioEntity>> = dao.obtenerTodos()

    fun toggleEstado(usuario: UsuarioEntity) = viewModelScope.launch {
        dao.cambiarEstado(usuario.username, !usuario.activo)
    }

    fun crearTecnico(username: String, nombre: String, contrasena: String) = viewModelScope.launch {
        dao.insertar(
            UsuarioEntity(
                username = username,
                contrasena = contrasena,
                nombre = nombre,
                rol = "tecnico",
                activo = true
            )
        )
    }
}