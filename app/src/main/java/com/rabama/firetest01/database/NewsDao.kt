package com.rabama.firetest01.database

import androidx.room.Dao
import androidx.room.Query
import com.rabama.firetest01.database.entities.Article

// Define las querys que se pueden realizar en la base de datos
@Dao
interface NewsDao: MyDao {
    @Query("SELECT * FROM article_entity")
    override fun getAllArticles(): MutableList<Article>

    @Query("DELETE FROM article_entity")
    override fun deleteAllArticles(): Int
}