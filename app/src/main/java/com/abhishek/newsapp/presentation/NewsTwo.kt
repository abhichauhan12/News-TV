package com.abhishek.newsapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.Text
import com.abhishek.newsapp.data.network.response.Article
import kotlinx.coroutines.launch


@Composable
fun NewsTwo(){

    val newsViewModel : NewsViewModel = hiltViewModel()
    val newsData = newsViewModel.newsData.collectAsState().value

    val lazyListState = rememberTvLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        newsViewModel.getNews()
    }

    Column(modifier = Modifier
        .padding(24.dp)
        .focusable(true)
    ) {
        Text(text = "Top News" , fontSize = 40.sp)
        Spacer(modifier = Modifier.height(24.dp))

        newsData?.let {response ->

            TvLazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .focusable(true)
                    .onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.DirectionDown) {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(
                                lazyListState.firstVisibleItemIndex + 1
                            )
                        }
                        true
                    } else {
                        false
                    }
                }
            ) {
                items(response.articles) { article ->
                    Spacer(modifier = Modifier.height(16.dp))
                    NewsItemTwo(article)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

    }


}




@Composable
fun NewsItemTwo(article: Article){

    val title = article.title
    val authorName = article.source.name

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray, RoundedCornerShape(16.dp))
            .padding(32.dp)
            .focusable(true)

    ){

        val (news , sourceName) =createRefs()

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













