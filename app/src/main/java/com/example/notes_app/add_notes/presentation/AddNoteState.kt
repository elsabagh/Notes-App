package com.example.notes_app.add_notes.presentation

data class AddNoteState(
    val title: String = "",
    val description: String = "",
    val imageURL: String = "",

    val isImagesDialogShowing: Boolean = false,
    val imageList: List<String> = emptyList(),
    val searchImagesQuery: String = "",
    val isLoadingImages: Boolean = false,
)