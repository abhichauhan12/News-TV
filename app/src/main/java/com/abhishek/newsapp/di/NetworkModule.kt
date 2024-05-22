package com.abhishek.newsapp.di

import com.abhishek.newsapp.data.network.APIService
import com.abhishek.newsapp.data.network.apis.NewsApi
import com.abhishek.newsapp.utils.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Making singleton objects with hilt
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    //Providing a retrofit instance
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) //Setting the base URL for the API
            .addConverterFactory(GsonConverterFactory.create(Gson()))  // Adding a converter factory for serialization and deserialization
            .build() // Building the Retrofit instance
    }

    // Providing the APIService instance
    @Provides
    fun provideAPIService(retrofit: Retrofit) : APIService {
        // Creating APIService with Retrofit instance
        return APIService(retrofit = retrofit)
    }

    // Providing the NewsApi instance
    @Provides
    fun provideNewsAPI(apiService: APIService) : NewsApi {
        // Creating NewsApi using APIService
        return apiService.createAPI(NewsApi::class.java)
    }






}