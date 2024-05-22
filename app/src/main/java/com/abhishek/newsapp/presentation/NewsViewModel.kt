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

//Acts as the bridge between the UI and NetworkRepository for fetching news data
@HiltViewModel
class NewsViewModel @Inject constructor(
    // Injecting NetworkRepository using Hilt
    private val networkRepo : NetworkRepository
) : ViewModel() {

    // MutableStateFlow to hold the news data
    private val _newsData = MutableStateFlow<NewsData?>(null)
    // Publicly exposed StateFlow for observing the news data
    val newsData = _newsData.asStateFlow()

    // Function to fetch news data
    fun getNews(){
        // Launch a coroutine in the ViewModel's scope
        viewModelScope.launch {
            // Switch to IO dispatcher for network operation
            withContext(Dispatchers.IO){
                // Get the news data from the repository
                val response = networkRepo.getNewsData()
                // Update the MutableStateFlow with the fetched data
                _newsData.value =response.data
            }
        }
    }

}