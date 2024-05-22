package com.abhishek.newsapp.domain.core

import com.abhishek.newsapp.data.network.FetchStatus

// data class to represent the result of a network operation with fetch status
data class Result<T>(val status : FetchStatus, val data: T?)
