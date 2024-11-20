package com.example.notes_app.core.domain.repository

import com.example.notes_app.core.domain.model.Images

interface ImagesRepository {

    suspend fun searchImages(query: String): Images?

}