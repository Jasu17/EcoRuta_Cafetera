package com.ecoruta.cafetera.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ecoruta.cafetera.data.local.dao.FincaDao
import com.ecoruta.cafetera.data.local.dao.UsuarioDao
import com.ecoruta.cafetera.data.local.entity.FincaEntity
import com.ecoruta.cafetera.data.local.entity.UsuarioEntity

@Database(
    entities = [FincaEntity::class, UsuarioEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fincaDao(): FincaDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS usuarios (
                        username TEXT NOT NULL PRIMARY KEY,
                        contrasena TEXT NOT NULL,
                        nombre TEXT NOT NULL,
                        rol TEXT NOT NULL,
                        activo INTEGER NOT NULL DEFAULT 1
                    )
                """)
                // Insertar usuarios por defecto
                db.execSQL("INSERT OR IGNORE INTO usuarios VALUES ('tecnico', '1234', 'Técnico Campo', 'tecnico', 1)")
                db.execSQL("INSERT OR IGNORE INTO usuarios VALUES ('admin', 'admin123', 'Administrador', 'admin', 1)")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecoruta_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}