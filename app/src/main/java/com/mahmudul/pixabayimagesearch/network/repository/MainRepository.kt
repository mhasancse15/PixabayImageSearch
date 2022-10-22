package com.mahmudul.pixabayimagesearch.network.repository

import com.mahmudul.pixabayimagesearch.network.model.PixabayResponse
import com.mahmudul.pixabayimagesearch.network.service.ApiService
import com.mahmudul.pixabayimagesearch.util.Constant
import com.mahmudul.pixabayimagesearch.util.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun getQueryItems(q:String): Resource<PixabayResponse> {
        return  try{

            val result = apiService.getQueryImages(query = q, apiKey = Constant.KEY, imageType = "photo")
            Resource.Success(data = result)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }


}