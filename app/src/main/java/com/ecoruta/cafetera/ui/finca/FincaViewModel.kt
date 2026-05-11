package com.ecoruta.cafetera.ui.finca

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ecoruta.cafetera.data.local.AppDatabase
import com.ecoruta.cafetera.data.local.entity.FincaEntity
import com.ecoruta.cafetera.data.repository.FincaRepository
import kotlinx.coroutines.launch

class FincaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FincaRepository
    val fincas: LiveData<List<FincaEntity>>
    val tecnicoActual: String

    init {
        val dao = AppDatabase.getInstance(application).fincaDao()
        repository = FincaRepository(dao)

        // Leer el usuario de SharedPreferences
        val prefs = application.getSharedPreferences("ecoruta_prefs", Context.MODE_PRIVATE)
        tecnicoActual = prefs.getString("usuario_actual", "tecnico") ?: "tecnico"

        //fincas = repository.obtenerPorTecnico(tecnicoActual)
        fincas = repository.obtenerTodas()
    }

    fun guardarFinca(finca: FincaEntity) = viewModelScope.launch {
        repository.guardar(finca)
    }

    fun eliminarFinca(finca: FincaEntity) = viewModelScope.launch {
        repository.eliminar(finca)
    }
}