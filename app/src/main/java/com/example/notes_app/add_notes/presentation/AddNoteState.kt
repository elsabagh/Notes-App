package com.example.notes_app.add_notes.presentation

data class AddNoteState(
    val title: String = "",
    val description: String = "",
    val imageURL: String = "",

    val isImagesDialogShown: Boolean = false,
    val imageList: List<String> = emptyList(),
    val searchImagesQuery: String = "",
    val isLoadingImages: Boolean = false,
)