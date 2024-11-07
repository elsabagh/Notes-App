package com.example.notes_app.core.domain.model

data class NoteItem(
    val id: Int = 0,
    var title: String,
    var description: String,
    var imageUrl: String,
    var dateAdded: Long,
)
