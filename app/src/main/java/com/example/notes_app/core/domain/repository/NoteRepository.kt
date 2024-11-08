package com.example.notes_app.core.domain.repository

import com.example.notes_app.core.domain.model.NoteItem

interface NoteRepository {

    suspend fun upsertNote(noteItem: NoteItem)

    suspend fun deleteNote(noteItem: NoteItem)

    suspend fun getAllNotes(): List<NoteItem>

}