package com.example.notes_app.core.data.mapper

import com.example.notes_app.core.data.remote.dto.ImageListDto
import com.example.notes_app.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.previewURL ?: ""
        } ?: emptyList()
    )
}