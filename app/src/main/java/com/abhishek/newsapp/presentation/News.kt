package com.abhishek.newsapp.presentation

import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text
import com.abhishek.newsapp.data.network.response.Article
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



//Home screen composable function
@Preview(device = Devices.TV_1080p)
@Composable
fun News(){

    val newsViewModel : NewsViewModel = hiltViewModel()

    //Collecting newsData state from viewModel
    val newsData = newsViewModel.newsData.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    // Mutable state to track the loading status
    var isLoading by remember { mutableStateOf(true) }

    // Remember a FocusRequester to manage focus in the UI
    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current

    // Lambda function to load news data
    val loadNews = {
        coroutineScope.launch{
            Log.d("News", "Start loading news")
            isLoading = true
            try {
                // Fetch news data from the ViewModel
                newsViewModel.getNews()
            }catch (e:Exception){
                // Show error message as a Toast and in Logs
                Log.e("News", "Error loading news", e)
                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            }finally {
                // Simulate loading delay
                delay(1500L)
                Log.d("News", "Finished loading news")
                isLoading = false
                Log.d("News", "Finished loading news and isloading $isLoading")
            }
        }
    }

    // Load news data when the composable is first launched
    LaunchedEffect(Unit) {
        loadNews()
    }


    Column(modifier = Modifier
        .padding(24.dp)
        // Handle long press on DPAD_DOWN to refresh news
        .onKeyEvent {event ->
            if (event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_DOWN && event.nativeKeyEvent.isLongPress) {
                loadNews()
                true
            } else {
                false
            }
        }
    ) {


        Log.d("News", "1st isLoading: $isLoading")

        if (isLoading){

            Log.d("News", "isLoading in is loading: $isLoading")

            // Show loading indicator while news data is being fetched
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else{

            Log.d("News", " in else isLoading: $isLoading")

            // Display news data if it has been successfully fetched
            newsData?.let {response ->
                LazyColumn(
                    modifier = Modifier
                        .focusable(true)
                        .focusRequester(focusRequester)

                ) {
                    items(response.articles){article ->
                        Spacer(modifier = Modifier.height(16.dp))
                        NewsItem(article)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                LaunchedEffect(newsData) {
                    focusRequester.requestFocus()
                }

            }

        }



    }


}


//List item for news
@Composable
fun NewsItem(article: Article){

    val title = article.title
    val authorName = article.source.name

    // ConstraintLayout to arrange the components within the item
    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray, RoundedCornerShape(16.dp))
            .padding(32.dp)
            .focusable(true)
    ){

        val (news , sourceName) =createRefs()

        // Box to display the news title
        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(news) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                color = Color.White,
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Simple
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Box to display the source name of the news
        Box (modifier = Modifier
            .constrainAs(sourceName){
                top.linkTo(news.bottom)
                end.linkTo(parent.end)
            }){
            Text(
                text = "- $authorName" ,
                color = Color.LightGray,
                fontSize = 20.sp
            )
        }

    }

}













