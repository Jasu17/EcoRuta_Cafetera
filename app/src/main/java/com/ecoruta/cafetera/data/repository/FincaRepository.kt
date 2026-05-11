package com.ecoruta.cafetera.data.repository

import androidx.lifecycle.LiveData
import com.ecoruta.cafetera.data.local.dao.FincaDao
import com.ecoruta.cafetera.data.local.entity.FincaEntity

class FincaRepository(private val dao: FincaDao) {

    fun obtenerTodas(): LiveData<List<FincaEntity>> = dao.obtenerTodas()

    fun obtenerPorTecnico(tecnico: String): LiveData<List<FincaEntity>> =
        dao.obtenerPorTecnico(tecnico)

    suspend fun guardar(finca: FincaEntity) = dao.insertar(finca)

    suspend fun actualizar(finca: FincaEntity) = dao.actualizar(finca)

    suspend fun eliminar(finca: FincaEntity) = dao.eliminar(finca)

    suspend fun obtenerPorId(id: Int): FincaEntity? = dao.obtenerPorId(id)
}