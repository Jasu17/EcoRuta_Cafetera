package com.ecoruta.cafetera.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ecoruta.cafetera.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios ORDER BY rol, username")
    fun obtenerTodos(): LiveData<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE username = :username AND contrasena = :contrasena AND activo = 1")
    suspend fun login(username: String, contrasena: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity)

    @Update
    suspend fun actualizar(usuario: UsuarioEntity)

    @Query("UPDATE usuarios SET activo = :activo WHERE username = :username")
    suspend fun cambiarEstado(username: String, activo: Boolean)
}