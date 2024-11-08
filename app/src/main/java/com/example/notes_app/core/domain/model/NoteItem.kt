package com.example.notes_app.core.domain.model

data class NoteItem(
    var title: String,
    var description: String,
    var imageUrl: String,

    val dateAdded: Long,

    val id: Int = 0,
)
