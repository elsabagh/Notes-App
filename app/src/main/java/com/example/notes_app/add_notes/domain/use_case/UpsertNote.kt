package com.example.notes_app.add_notes.domain.use_case

import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.domain.repository.NoteRepository

class UpsertNote(
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
    ): Boolean {
        val note = NoteItem(
            title = title,
            description = description,
            dateAdded = System.currentTimeMillis(),
            imageUrl = imageUrl
        )

        if (title.isEmpty() || description.isEmpty()) {
            return false
        }

        noteRepository.upsertNote(note)
        return true

    }
}