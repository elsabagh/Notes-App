package com.example.notes_app.core.data.remote.api

import com.example.notes_app.core.data.remote.dto.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") key: String = API_KEY,

        ): ImageListDto?

    companion object {
        const val BASE_URL = "https://pixabay.com"
        const val API_KEY = "33874707-df87af9cfeeb68d5f6335075e"
    }

}