package com.example.notes_app.use_case

import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: NoteItem ) {
        noteRepository.deleteNote(note)

    }

}