package com.ecoruta.cafetera.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fincas")
data class FincaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombrePropietario: String,
    val nombreFinca: String,
    val municipio: String,
    val tipoCultivo: String,
    val hectareas: Double,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val tecnicoRegistra: String,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val sincronizado: Boolean = false
)