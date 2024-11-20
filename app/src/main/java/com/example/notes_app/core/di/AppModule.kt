package com.example.notes_app.core.di

import android.app.Application
import androidx.room.Room
import com.example.notes_app.add_notes.domain.use_case.UpsertNote
import com.example.notes_app.core.data.local.NoteDb
import com.example.notes_app.core.data.remote.api.ImagesApi
import com.example.notes_app.core.data.repository.NoteRepositoryImpl
import com.example.notes_app.core.domain.repository.NoteRepository
import com.example.notes_app.note_list.domain.use_case.DeleteNote
import com.example.notes_app.note_list.domain.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideUpsertNoteUseCse(
        noteRepository: NoteRepository,
    ): UpsertNote {
        return UpsertNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImagesApi.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }
}