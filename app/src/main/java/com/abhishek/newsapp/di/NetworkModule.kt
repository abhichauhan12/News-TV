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

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @Provides
    fun provideAPIService(retrofit: Retrofit) : APIService {
        return APIService(retrofit = retrofit)
    }

    @Provides
    fun provideNewsAPI(apiService: APIService) : NewsApi {
        return apiService.createAPI(NewsApi::class.java)
    }






}