package com.example.notes_app.add_notes.domain.use_case

import com.example.notes_app.add_notes.presentation.util.Resource
import com.example.notes_app.core.domain.model.Images
import com.example.notes_app.core.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchImages(
    private val imagesRepository: ImagesRepository,
) {
    suspend operator fun invoke(
        query: String,
    ): Flow<Resource<Images>> {

        return flow {
            emit(Resource.Loading(true))

            if (query.isEmpty()) {
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            val images = try {
                imagesRepository.searchImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            images?.let {
                emit(Resource.Success(it))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error())
            emit(Resource.Loading(false))


        }

    }
}