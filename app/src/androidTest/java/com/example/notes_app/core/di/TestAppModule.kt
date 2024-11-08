package com.example.notes_app.core.di

import android.app.Application
import androidx.room.Room
import com.example.notes_app.core.data.local.NoteDb
import com.example.notes_app.core.data.repository.FakeAndroidNoteRepository
import com.example.notes_app.core.domain.repository.NoteRepository
import com.example.notes_app.use_case.DeleteNote
import com.example.notes_app.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.inMemoryDatabaseBuilder(
            application,
            NoteDb::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return FakeAndroidNoteRepository()
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