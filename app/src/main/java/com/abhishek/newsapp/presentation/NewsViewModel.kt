package com.abhishek.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.newsapp.data.network.response.NewsData
import com.abhishek.newsapp.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val networkRepo : NetworkRepository
) : ViewModel() {

    private val _newsData = MutableStateFlow<NewsData?>(null)
    val newsData = _newsData.asStateFlow()

    fun getNews(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val response = networkRepo.getNewsData()
                _newsData.value =response.data
            }
        }
    }


}