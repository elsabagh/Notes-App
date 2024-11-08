package com.example.notes_app.use_case

import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.domain.repository.NoteRepository

class GetAllNotes(
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(
        isOrderByTitle: Boolean,
    ): List<NoteItem> {
        return if (isOrderByTitle) {
            noteRepository.getAllNotes().sortedBy { it.title.lowercase() }
        } else {
            noteRepository.getAllNotes().sortedBy { it.dateAdded }
        }
    }
}