package com.ecoruta.cafetera.ui.finca

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ecoruta.cafetera.data.local.AppDatabase
import com.ecoruta.cafetera.data.local.entity.FincaEntity
import com.ecoruta.cafetera.data.repository.FincaRepository
import com.ecoruta.cafetera.utils.SessionManager
import kotlinx.coroutines.launch

class FincaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FincaRepository
    val fincas: LiveData<List<FincaEntity>>
    val tecnicoActual: String

    val esAdmin : Boolean


    init {
        val dao = AppDatabase.getInstance(application).fincaDao()
        repository = FincaRepository(dao)
        tecnicoActual = SessionManager.getUsuario(application)
        esAdmin = SessionManager.esAdmin(application)

        fincas = if (esAdmin) {
            repository.obtenerTodas()
        } else {
            repository.obtenerPorTecnico(tecnicoActual)
        }
    }

    fun guardarFinca(finca: FincaEntity) = viewModelScope.launch {
        repository.guardar(finca)
    }

    fun eliminarFinca(finca: FincaEntity) = viewModelScope.launch {
        repository.eliminar(finca)
    }
}