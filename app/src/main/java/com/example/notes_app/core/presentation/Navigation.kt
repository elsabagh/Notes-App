package com.example.notes_app.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes_app.add_notes.presentation.AddNoteScreen
import com.example.notes_app.note_list.presentation.NoteListScreen


@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.NoteList
    ) {

        composable<Screen.NoteList> {
            NoteListScreen(
                onNavigateToAddNote = {
                    navController.navigate(Screen.AddNote)
                }
            )
        }

        composable<Screen.AddNote> {
            AddNoteScreen(
                onSave = {
                    navController.popBackStack()
                }
            )
        }
    }
}
