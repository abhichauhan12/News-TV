package com.abhishek.newsapp.domain.core

import com.abhishek.newsapp.data.network.FetchStatus

data class Result<T>(val status : FetchStatus, val data: T?)
