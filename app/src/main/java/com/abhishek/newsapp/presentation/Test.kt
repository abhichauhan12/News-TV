package com.abhishek.newsapp.presentation
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.focusable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.LineBreak
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.tv.material3.Text
//import com.abhishek.newsapp.data.network.response.Article
//import com.abhishek.newsapp.utils.onDpadDownLongPress
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//
//@Composable
//fun News(){
//
//    val newsViewModel : NewsViewModel = hiltViewModel()
//    val newsData = newsViewModel.newsData.collectAsState().value
//
//    var isRefreshing by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        newsViewModel.getNews()
//    }
//
//    Column(modifier = Modifier
//        .focusable(true)
//        .padding(24.dp)
//        .onDpadDownLongPress {
//            scope.launch {
//                isRefreshing = true
//                newsViewModel.getNews()
//                isRefreshing = false
//            }
//        }
//    ) {
//
//        Text(text = "Top News" , fontSize = 40.sp)
//        Spacer(modifier = Modifier.height(24.dp))
//
//        PullToRefreshLazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .focusable(true),
//            items = newsData?.articles ?: emptyList(),
//            content = { article ->
//                NewsItem(article)
//            },
//            isRefreshing = isRefreshing,
//            onRefresh = {
//                scope.launch {
//                    isRefreshing = true
//                    newsViewModel.getNews()
//                    delay(3000L)
//                    isRefreshing = false
//                }
//            },
//        )
//
//        Button(
//            onClick = {
//                isRefreshing = true
//            },
//            modifier = Modifier.padding(16.dp)
//
//        ) {
//            Text(text = "Refresh")
//        }
//
//        }
//
//}
//
//
//@Composable
//fun NewsItem(article: Article){
//
//    val title = article.title
//    val authorName = article.source.name
//
//    ConstraintLayout (
//        modifier = Modifier
//            .focusable(true)
//            .fillMaxSize()
//            .background(Color.DarkGray, RoundedCornerShape(16.dp))
//            .padding(32.dp)
//    ){
//
//        val (news , sourceName) =createRefs()
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .constrainAs(news) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(parent.top)
//                }
//        ) {
//            Text(
//                text = title,
//                fontSize = 32.sp,
//                color = Color.White,
//                style = TextStyle.Default.copy(
//                    lineBreak = LineBreak.Simple
//                )
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Box (modifier = Modifier
//            .constrainAs(sourceName){
//                top.linkTo(news.bottom)
//                end.linkTo(parent.end)
//            }){
//            Text(
//                text = "- $authorName" ,
//                color = Color.LightGray,
//                fontSize = 20.sp
//            )
//        }
//
//    }
//
//}
//
//
