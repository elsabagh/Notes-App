package com.example.notes_app.core.di

import android.app.Application
import androidx.room.Room
import com.example.notes_app.core.data.local.NoteDb
import com.example.notes_app.core.data.repository.NoteRepositoryImpl
import com.example.notes_app.core.domain.repository.NoteRepository
import com.example.notes_app.note_list.domain.use_case.DeleteNote
import com.example.notes_app.note_list.domain.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.databaseBuilder(
            application,
            NoteDb::class.java,
            "note_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDb: NoteDb,
    ): NoteRepository {
        return NoteRepositoryImpl(noteDb)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCse(
        noteRepository: NoteRepository,
    ): GetAllNotes {
        return GetAllNotes(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCse(
        noteRepository: NoteRepository,
    ): DeleteNote {
        return DeleteNote(noteRepository)
    }

}