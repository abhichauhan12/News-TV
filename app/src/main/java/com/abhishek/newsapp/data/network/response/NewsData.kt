package com.abhishek.newsapp.data.network.response

data class NewsData(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)