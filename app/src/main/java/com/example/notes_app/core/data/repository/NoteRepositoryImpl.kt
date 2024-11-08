package com.example.notes_app.core.data.repository

import com.example.notes_app.core.data.local.NoteDb
import com.example.notes_app.core.data.mapper.toNoteEntityForDelete
import com.example.notes_app.core.data.mapper.toNoteEntityForInsert
import com.example.notes_app.core.data.mapper.toNoteItem
import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.domain.repository.NoteRepository

class NoteRepositoryImpl(
    noteDb: NoteDb,
) : NoteRepository {

    private val noteDao = noteDb.noteDao

    override suspend fun upsertNote(noteItem: NoteItem) {
        noteDao.upsertNoteEntity(noteItem.toNoteEntityForInsert())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteDao.deleteNoteEntity(noteItem.toNoteEntityForDelete())
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteDao.getAllNoteEntities().map {
            it.toNoteItem()
        }
    }
}