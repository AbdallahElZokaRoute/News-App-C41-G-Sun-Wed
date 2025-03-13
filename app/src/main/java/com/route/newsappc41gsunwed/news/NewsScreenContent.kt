package com.route.newsappc41gsunwed.news

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.route.newsappc41gsunwed.R
import com.route.data.api.model.ArticlesItem
import com.route.data.api.model.SourcesItem
import com.route.newsappc41gsunwed.ui.theme.gray
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.entities.SourcesItemEntity

// News Screen -> MVVM
@Composable
fun NewsScreenContent(
    endpointId: String, viewModel: NewsViewModel = hiltViewModel(), modifier: Modifier = Modifier
) {
    val sourcesList = viewModel.sourcesListStates
    val newsList = viewModel.newsListStates
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getSources(endpointId)
    }
    LaunchedEffect(viewModel.selectedSourceId.value) {
        viewModel.getNewsBySource()

    }
    Column(modifier) {
        if (sourcesList.isNotEmpty())
            SourcesTabRow(
                sourcesList = sourcesList,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                newsList.clear()
                viewModel.selectedSourceId.value = it
            }
        NewsList(newsList = newsList)
    }
    if (viewModel.isLoading.value)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    if (viewModel.errorState.value.isNotEmpty())
        ErrorDialog(viewModel = viewModel)
}

@Composable
fun ErrorDialog(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    AlertDialog(onDismissRequest = { viewModel.errorState.value = "" }, confirmButton = {
        TextButton(onClick = { viewModel.errorState.value = "" }) {
            Text(text = stringResource(R.string.ok))
        }
    }, containerColor = Color.White, text = {
        Text(text = viewModel.errorState.value, color = Color.Black, fontSize = 14.sp)
    }
    )
}


@Composable
fun NewsList(newsList: List<ArticlesItemEntity>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(newsList) {
            NewsCard(articleItem = it)
        }
    }
}

@Composable
fun NewsCard(articleItem: ArticlesItemEntity, modifier: Modifier = Modifier) {
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
        articleItem = ArticlesItemEntity(
            author = "Jon Haworth",
            title = "40-year-old man falls 200 feet to his death while canyoneering at national park",

            )
    )
}

@Composable
fun SourcesTabRow(
    sourcesList: List<SourcesItemEntity>,
    modifier: Modifier = Modifier,
    onSourceSelected: (id: String) -> Unit
) {
    // Create State ->
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit) {
        onSourceSelected(sourcesList.get(0).id ?: "")
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

@Preview(showSystemUi = true)
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow(
        sourcesList = listOf(
            SourcesItemEntity(name = "ABC News"),
            SourcesItemEntity(name = "Al-Jazeera"),
            SourcesItemEntity(name = "BBC News")
        )
    ) {

    }
}
