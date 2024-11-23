package com.example.notes_app.core.di

import android.app.Application
import androidx.room.Room
import com.example.notes_app.add_notes.domain.use_case.SearchImages
import com.example.notes_app.add_notes.domain.use_case.UpsertNote
import com.example.notes_app.core.data.local.NoteDb
import com.example.notes_app.core.data.remote.api.ImagesApi
import com.example.notes_app.core.data.repository.FakeAndroidImagesRepository
import com.example.notes_app.core.data.repository.FakeAndroidNoteRepository
import com.example.notes_app.core.data.repository.ImagesRepositoryImpl
import com.example.notes_app.core.domain.repository.ImagesRepository
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

    @Provides
    @Singleton
    fun provideImagesRepository(): ImagesRepository {
        return FakeAndroidImagesRepository()
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCse(
        imagesRepository: ImagesRepository,
    ): SearchImages {
        return SearchImages(imagesRepository)
    }
}