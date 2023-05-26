package com.rabama.firetest01.model

import com.rabama.firetest01.database.entities.Article

// Crea la entidad NewsResponse con los campos que se guardarán en la base de datos
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)