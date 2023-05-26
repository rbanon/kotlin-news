package com.rabama.firetest01.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Crea la entidad Article con los campos que se guardarán en la base de datos
@Entity(tableName = "article_entity")
data class Article(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
)
