package com.example.notes_app.note_list.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.notes_app.core.di.AppModule
import com.example.notes_app.core.presentation.MainActivity
import com.example.notes_app.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun insertNote_noteIsDisplayedInList() {
        insertNote(1)
        assertNoteIsDisplayed(1)
    }

    @Test
    fun deleteNote_noteIsNotDisplayedInList() {
        insertNote(1)
        deleteNote(1)
        assertNoteIsNotDisplayed(1)
    }

    @Test
    fun noteListScreenEndToEndTest() {
        for (i in 1..5) {
            insertNote(i)
            assertNoteIsDisplayed(i)
            if (i == 2) {
                deleteNote(i)
                assertNoteIsNotDisplayed(i)
            }
        }

        insertNote(6)
        assertNoteIsDisplayed(6)

        for (i in listOf(1, 3, 4, 5, 6)) {
            assertNoteIsDisplayed(i)
        }

        deleteNote(5)
        assertNoteIsNotDisplayed(5)

        for (i in listOf(1, 3, 4, 6)) {
            assertNoteIsDisplayed(i)
        }
    }

    private fun insertNote(noteNumber: Int) {

        composeRule.onNodeWithTag(TestTags.ADD_NOTE_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title $noteNumber")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description $noteNumber")

        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON)
            .performClick()
    }

    private fun deleteNote(noteNumber: Int) {
        composeRule.onNodeWithContentDescription(
            TestTags.DELETE_NOTE + "title $noteNumber"
        ).performClick()
    }

    private fun assertNoteIsDisplayed(noteNumber: Int) {
        composeRule.onNodeWithText("title $noteNumber")
            .assertIsDisplayed()
    }

    private fun assertNoteIsNotDisplayed(noteNumber: Int) {
        composeRule.onNodeWithText("title $noteNumber")
            .assertIsNotDisplayed()
    }


}