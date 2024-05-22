package com.abhishek.newsapp.data.network.apis

import com.abhishek.newsapp.data.network.response.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    fun getNewsData(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String,
    ) : Call<NewsData>

}