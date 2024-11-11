package com.example.notes_app.add_notes.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes_app.MainCoroutineRule
import com.example.notes_app.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
    }

    @Test
    fun `upsert note with empty title, returns false`() = runTest {
        val result = upsertNote.invoke(
            title = "",
            description = "test description",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `upsert note with empty description, returns false`() = runTest {
        val result = upsertNote.invoke(
            title = "test title",
            description = "",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `upsert note a valid, returns true`() = runTest {
        val result = upsertNote.invoke(
            title = "test title",
            description = "test description",
            imageUrl = "test imageUrl"
        )
        assertThat(result).isTrue()
    }

}