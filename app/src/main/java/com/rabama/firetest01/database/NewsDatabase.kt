package com.rabama.firetest01.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rabama.firetest01.database.entities.Article

// Define la entidad Article y su versión de base de datos
@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    // Define una variable estática para almacenar una instancia de NewsDao
    companion object {
        private var instance: NewsDao? = null

        // Define un método estático para obtener la instancia de NewsDao, crea la base de datos si no existe
        fun getInstance(context: Context): NewsDao {
            // Si instance es nulo, crea una instancia de NewsDao y la almacena en la variable instance
            return instance ?: Room.databaseBuilder(context, NewsDatabase::class.java, "news-db")
                .allowMainThreadQueries()
                .build()
                .newsDao().also { instance = it }
        }
    }
}