package com.rabama.firetest01.service

import com.rabama.firetest01.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Define las funciones que se pueden realizar en la API
interface NewsApiService {

    // Realiza la conexión con la API
    companion object ApiService {
        const val API_KEY = "80516ec779024eaa98c16ad3ece602df"
        private const val BASE_URL = "https://newsapi.org/v2/"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val newsApi: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }

    // Noticias en español (por defecto) que contengan la palabra clave
    @GET("everything?language=es&apiKey=$API_KEY")
    suspend fun getNews(@Query("q") keyword: String): NewsResponse

    // Noticias de una categoría en específico (en inglés)
    /*
     * Se puede utilizar sin country=us,
     * pero la API devolvería muchas noticias sin imagen
     */
    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getNewsByCategory(@Query("category") category: String): NewsResponse
}