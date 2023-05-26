package com.rabama.firetest01.viewmodel.news

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.rabama.firetest01.database.entities.Article
import com.rabama.firetest01.service.NewsRepository
import kotlinx.coroutines.launch


class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application

    // Implementa patrón Dao
    private val repository = NewsRepository()

    // Declara un LiveData para almacenar la lista de noticias
    val newsListLiveData: MutableLiveData<MutableList<Article>> = MutableLiveData()

    // Obtiene todas las noticias de la API
    fun getAllArticles(keyword: String? = "all") {
        viewModelScope.launch {
            try {
                // Llama a la función getNews para obtener las noticias
                val response = repository.getNews(keyword ?: "all")
                if(response.status == "ok") {
                    // Si la respuesta es "ok", actualiza el LiveData con la lista de noticias
                    newsListLiveData.postValue(response.articles.toMutableList())
                } else {
                    // Si la respuesta no es "ok", muestra un mensaje de error
                    Toast.makeText(context, "Error: ${response.status}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Obtiene todas las noticias de una categoría
    fun getArticlesByCategory(category: String) {
        viewModelScope.launch {
            try {
                // Llama a la función getNews del repositorio para obtener las noticias
                val response = repository.getNewsByCategory(category)
                if(response.status == "ok") {
                    // Si la respuesta es "ok", actualiza el objeto LiveData con la lista de noticias
                    newsListLiveData.postValue(response.articles.toMutableList())
                } else {
                    // Si la respuesta no es "ok", muestra un mensaje de error
                    Toast.makeText(context, "Error: ${response.status}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}