package com.route.newsappc41gsunwed

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.route.newsappc41gsunwed.api.ApiManager
import com.route.newsappc41gsunwed.api.model.ArticlesItem
import com.route.newsappc41gsunwed.api.model.NewsResponse
import com.route.newsappc41gsunwed.api.model.SourcesItem
import com.route.newsappc41gsunwed.api.model.SourcesResponse
import com.route.newsappc41gsunwed.ui.theme.NewsAppC41GSunWedTheme
import com.route.newsappc41gsunwed.ui.theme.gray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppC41GSunWedTheme {
                // Don't Call API HERE
                // Toolbars or Bottom Navigation Views or Navigation Drawers
                // Use Scaffold
                NewsScreenContent()
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(1.dp))
                Text(text = title)
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        R.string.news_search
                    )
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(R.string.navigation_menu_icon)
            )
        },
    )
}

@Preview
@Composable
private fun NewsToolbarPreview() {
    NewsToolbar(title = "General")
}

@Composable
fun NewsScreenContent() {

    Scaffold(
        topBar = {
            NewsToolbar(title = "General")
        },
        containerColor = Color.Black
    ) { paddingValues ->
        paddingValues
        val selectedSourceId = remember {
            mutableStateOf("")
        }
        val sourcesListStates = remember {
            mutableStateListOf<SourcesItem>()
        }
        val newsListStates = remember {
            mutableStateListOf<ArticlesItem>()
        }
        val errorState = remember {
            mutableStateOf("")
        }

        LaunchedEffect(Unit) {
            getSources(onSuccess = { sourcesList ->
                sourcesListStates.addAll(sourcesList)
            }, onFailure = {
                errorState.value = it
            })

        }
        LaunchedEffect(selectedSourceId.value) {
            if (selectedSourceId.value.isNotEmpty())
                ApiManager.newsServices.getNewsBySource(selectedSourceId.value)
                    .enqueue(object : Callback<NewsResponse> {
                        override fun onResponse(
                            p0: Call<NewsResponse>,
                            response: Response<NewsResponse>
                        ) {
                            val list = response.body()?.articles
                            if (list != null) {
                                newsListStates.clear()
                                newsListStates.addAll(list)
                            }
                        }

                        override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SourcesTabRow(
                sourcesList = sourcesListStates,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                selectedSourceId.value = it
            }
            if (selectedSourceId.value.isNotEmpty())
                NewsList(newsList = newsListStates)
        }
    }
}

@Composable
fun NewsList(newsList: List<ArticlesItem>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(newsList) {
            NewsCard(articleItem = it)
        }
    }
}

@Composable
fun NewsCard(articleItem: ArticlesItem, modifier: Modifier = Modifier) {
    Card(
        modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),

        ) {
        AsyncImage(
            model = articleItem.urlToImage,
            contentDescription = "Specific News Image ",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = articleItem.title ?: "",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = stringResource(id = R.string.by) + "${articleItem.author}",
            color = gray,
            fontSize = 10.sp,
            fontWeight = FontWeight.W500
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun NewsCardPreview() {
    NewsCard(
        articleItem = ArticlesItem(
            author = "Jon Haworth",
            title = "40-year-old man falls 200 feet to his death while canyoneering at national park",

            )
    )
}

@Composable
fun SourcesTabRow(
    sourcesList: List<SourcesItem>,
    modifier: Modifier = Modifier,
    onSourceSelected: (id: String) -> Unit
) {
    // Create State ->
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedModifier = Modifier.drawBehind {
        val strokeWidthPx = 2.dp.toPx()
        val verticalOffset = size.height - 2.sp.toPx()
        drawLine(
            color = Color.White,
            strokeWidth = strokeWidthPx,
            start = Offset(0f, verticalOffset),
            end = Offset(size.width, verticalOffset)
        )
    }
    LazyRow(modifier.background(Color.Black)) {
        itemsIndexed(sourcesList) { index, sourceItem ->
            Tab(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White,
                selected = selectedItemIndex.intValue == index,
                onClick = {
                    Log.e("TAG", "SourcesTabRow:  $index")
                    onSourceSelected(sourceItem.id ?: "")
                    selectedItemIndex.intValue = index
                },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = sourceItem.name ?: "",
                    color = Color.White,
                    modifier = if (selectedItemIndex.intValue == index) selectedModifier else Modifier
                )
//                    if (selectedItemIndex.intValue == index)
//                        HorizontalDivider(
//                            modifier = Modifier.height(2.dp),
//                            color = Color.White,
//                            thickness = 1.dp
//                        )

            }
        }
    }
}

fun getSources(
    onSuccess: (sources: List<SourcesItem>) -> Unit,
    onFailure: (message: String) -> Unit
) {
    ApiManager.newsServices.getSources()
//                        .execute() // Run On Main Thread
        .enqueue(object : Callback<SourcesResponse> {
            override fun onFailure(
                p0: Call<SourcesResponse>,
                throwable: Throwable
            ) {
                Log.e("TAG", "onFailure: ${throwable.message}")

                onFailure(throwable.message ?: "Something Went Wrong")
            }

            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                Log.e("TAG", "onResponse: ${response}")
                Log.e("TAG", "onResponse: ${response.body()?.sources}")
                val list = response.body()?.sources
                if (list?.isNotEmpty() == true)
                    onSuccess(list)
            }
        }) // Run on Background Thread and Returns Result On Main Thread
}

@Preview(showSystemUi = true)
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow(
        sourcesList = listOf(
            SourcesItem(name = "ABC News"),
            SourcesItem(name = "Al-Jazeera"),
            SourcesItem(name = "BBC News")
        )
    ) {

    }
}

