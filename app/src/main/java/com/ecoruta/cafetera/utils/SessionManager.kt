package com.ecoruta.cafetera.utils

import android.content.Context

object SessionManager {

    private const val PREFS_NAME = "ecoruta_prefs"
    private const val KEY_SESION_ACTIVA = "sesion_activa"
    private const val KEY_USUARIO = "usuario_actual"
    private const val KEY_ROL = "rol_actual"

    fun guardarSesion(context: Context, usuario: String, rol: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SESION_ACTIVA, true)
            .putString(KEY_USUARIO, usuario)
            .putString(KEY_ROL, rol)
            .apply()
    }

    fun cerrarSesion(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    fun sesionActiva(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_SESION_ACTIVA, false)

    fun getUsuario(context: Context): String =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USUARIO, "") ?: ""

    fun getRol(context: Context): String =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ROL, "tecnico") ?: "tecnico"

    fun esAdmin(context: Context): Boolean = getRol(context) == "admin"
}