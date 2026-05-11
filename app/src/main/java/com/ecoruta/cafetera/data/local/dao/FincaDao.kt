package com.ecoruta.cafetera.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ecoruta.cafetera.data.local.entity.FincaEntity

@Dao
interface FincaDao {

    @Query("SELECT * FROM fincas ORDER BY fechaRegistro DESC")
    fun obtenerTodas(): LiveData<List<FincaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(finca: FincaEntity)

    @Update
    suspend fun actualizar(finca: FincaEntity)

    @Delete
    suspend fun eliminar(finca: FincaEntity)

    @Query("SELECT * FROM fincas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): FincaEntity?
}