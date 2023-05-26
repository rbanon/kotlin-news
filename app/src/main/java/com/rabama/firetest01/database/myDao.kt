package com.rabama.firetest01.database

import com.rabama.firetest01.database.entities.Article

// Define las funciones que se pueden realizar en la base de datos
interface MyDao {
    fun getAllArticles(): MutableList<Article>
    fun deleteAllArticles(): Int
}
