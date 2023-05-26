package com.rabama.firetest01.service

// Define las funciones que se pueden realizar con la API
class NewsRepository {

    private val newsApiService = NewsApiService.newsApi

    suspend fun getNews(keyword: String) = newsApiService.getNews(keyword)

    suspend fun getNewsByCategory(category: String) = newsApiService.getNewsByCategory(category)

}