package com.ecoruta.cafetera.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val username: String,
    val contrasena: String,
    val nombre: String,
    val rol: String, // "tecnico" o "admin"
    val activo: Boolean = true
)