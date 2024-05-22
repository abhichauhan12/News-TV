package com.abhishek.newsapp.data.network.response


// api response data class
data class NewsData(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)