package com.mahmudul.pixabayimagesearch.network.service

import com.mahmudul.pixabayimagesearch.network.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getQueryImages(
        @Query("q") query:String,
        @Query("key") apiKey:String,
        @Query("image_type") imageType:String
    ): PixabayResponse

}