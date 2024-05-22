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

//NetworkRepository class is responsible for fetching news data from an API
class NetworkRepository @Inject constructor(
    // Injecting the NewsApi instance using Hilt
    private val newsApi: NewsApi,
) {

    // Getting the API key from the build configuration
    private val newsApiKey = BuildConfig.NEWS_KEY

    // Suspending function to get news data, which returns a Result<NewsData>
    suspend fun getNewsData() :Result<NewsData>{

        // Switching the context to IO for network operations
        return withContext(Dispatchers.IO){
            try {

                // Making a synchronous network request to fetch news data
                val response = newsApi
                    .getNewsData(COUNTRY , newsApiKey)
                    .execute()

                // Getting the response body
                val responseBody =response.body()
                Log.d("APISuccess", "Response: $responseBody")

                // Checking if the response is successful and the body is not null
                if (response.isSuccessful && responseBody != null){
                    Log.d("data" , "$responseBody")
                    // Returning the fetched data wrapped in a Result object with a FETCHED status
                    Result(FetchStatus.FETCHED, responseBody)
                }else
                    // Returning a failure result with an appropriate message
                    // if the response is not successful
                    Result(FetchStatus.FAILURE("Failed to fetch data: ${response.code()}"), null)
            }catch (e: Exception){

                // Logging the exception and returning a failure result
                // with the exception message
                Log.d("API Error", "Failed to fetch data: ${e.message}")
                Result(FetchStatus.FAILURE(e.message), null)
            }
        }
    }



}