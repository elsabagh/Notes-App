package com.example.notes_app.core.data.repository

import com.example.notes_app.core.data.mapper.toImages
import com.example.notes_app.core.data.remote.api.ImagesApi
import com.example.notes_app.core.domain.model.Images
import com.example.notes_app.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi,
) : ImagesRepository {
    override suspend fun searchImages(
        query: String,
    ): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}