package com.abhishek.newsapp.domain.repository

import android.util.Log
import com.abhishek.newsapp.BuildConfig
import com.abhishek.newsapp.data.network.FetchStatus
import com.abhishek.newsapp.domain.core.Result
import com.abhishek.newsapp.data.network.apis.NewsApi
import com.abhishek.newsapp.data.network.response.NewsData
import com.abhishek.newsapp.utils.COUNTRY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val newsApi: NewsApi,
) {

    private val newsApiKey = BuildConfig.NEWS_KEY

    suspend fun getNewsData() :Result<NewsData>{
        return withContext(Dispatchers.IO){
            try {

                val response = newsApi
                    .getNewsData(COUNTRY , newsApiKey)
                    .execute()

                val responseBody =response.body()
                Log.d("APISuccess", "Response: $responseBody")

                if (response.isSuccessful && responseBody != null){
                    Log.d("data" , "$responseBody")
                    Result(FetchStatus.FETCHED, responseBody)
                }else
                    Result(FetchStatus.FAILURE("Failed to fetch data: ${response.code()}"), null)
            }catch (e: Exception){
                Log.d("API Error", "Failed to fetch data: ${e.message}")
                Result(FetchStatus.FAILURE(e.message), null)
            }
        }
    }



}