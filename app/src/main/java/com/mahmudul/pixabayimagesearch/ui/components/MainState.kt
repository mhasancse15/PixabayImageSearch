package com.mahmudul.pixabayimagesearch.ui.components

import com.mahmudul.pixabayimagesearch.network.model.Hit


data class MainState(
    val isLoading:Boolean=false,
    val data:List<Hit> = emptyList(),
    val error:String=""
)