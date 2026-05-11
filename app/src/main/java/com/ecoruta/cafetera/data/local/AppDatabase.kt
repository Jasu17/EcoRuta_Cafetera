package com.ecoruta.cafetera.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ecoruta.cafetera.data.local.dao.FincaDao
import com.ecoruta.cafetera.data.local.entity.FincaEntity

@Database(entities = [FincaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fincaDao(): FincaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecoruta_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}