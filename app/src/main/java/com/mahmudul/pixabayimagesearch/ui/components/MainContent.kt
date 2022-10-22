package com.mahmudul.pixabayimagesearch.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mahmudul.pixabayimagesearch.network.model.Hit
import timber.log.Timber


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MainContent(viewModel: MainViewModel = hiltViewModel()) {

    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = viewModel.list.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {

            OutlinedTextField(
                value = query.value, onValueChange = {
                    query.value = it
                    viewModel.getImageList(query.value)

                }, enabled = true,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                label = { Text(text = "Search here...") },
                modifier = Modifier.fillMaxWidth()
            )

            if (result.isLoading) {
                Timber.tag("TAG").d("MainContent: in the loading");
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (result.error.isNotBlank()) {
                Timber.tag("TAG").d("MainContent: ${result.error}");
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = viewModel.list.value.error
                    )
                }
            }

            if (result.data.isNotEmpty()) {
                Timber.tag("TAG").d("MainContent: data list size  ${result.data.size}");
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp)
                ){
                    viewModel.list.value.data.let { hits ->
                        items(hits) { MainContentItem(it) }
                    }
                }
            }
        }
    }
}


@Composable
fun MainContentItem(hit: Hit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = hit.largeImageURL,
                builder = {
                    allowHardware(false)
                }),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}