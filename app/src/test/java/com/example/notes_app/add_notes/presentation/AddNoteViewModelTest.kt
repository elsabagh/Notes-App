package com.example.notes_app.add_notes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes_app.MainCoroutineRule
import com.example.notes_app.add_notes.domain.use_case.SearchImages
import com.example.notes_app.add_notes.domain.use_case.UpsertNote
import com.example.notes_app.core.data.repository.FakeImagesRepository
import com.example.notes_app.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNotesRepository: FakeNoteRepository
    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var addNoteViewModel: AddNoteViewModel

    @Before
    fun setup() {
        fakeNotesRepository = FakeNoteRepository()
        fakeImagesRepository = FakeImagesRepository()
        val upsertNote = UpsertNote(fakeNotesRepository)
        val searchImages = SearchImages(fakeImagesRepository)
        addNoteViewModel = AddNoteViewModel(upsertNote, searchImages)

    }

    @Test
    fun `upsert note with empty title, returns false`() = runTest {
        val result = addNoteViewModel.upsertNote(
            title = "",
            description = "test description",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `upsert note with empty description, returns false`() = runTest {
        val result = addNoteViewModel.upsertNote(
            title = "test title",
            description = "",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `upsert note a valid, returns true`() = runTest {
        val result = addNoteViewModel.upsertNote(
            title = "test title",
            description = "test description",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        addNoteViewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(
            addNoteViewModel.addNoteState.value.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query but with error, image list is empty`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)

        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(
            addNoteViewModel.addNoteState.value.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query, image list is not empty`() = runTest {

        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(
            addNoteViewModel.addNoteState.value.imageList.isNotEmpty()
        ).isTrue()
    }

}