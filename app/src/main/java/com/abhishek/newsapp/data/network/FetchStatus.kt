package com.abhishek.newsapp.data.network

sealed class FetchStatus {
    data object UNDEFINED : FetchStatus()
    data object FETCHING : FetchStatus()
    data object FETCHED : FetchStatus()
    class FAILURE(val message: String?) : FetchStatus()
}