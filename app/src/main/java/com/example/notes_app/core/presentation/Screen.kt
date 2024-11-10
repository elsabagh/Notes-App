package com.example.notes_app.core.presentation

sealed interface Screen {

    @kotlinx.serialization.Serializable
    data object NoteList : Screen

    @kotlinx.serialization.Serializable
    data object AddNote : Screen
}