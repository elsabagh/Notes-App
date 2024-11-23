package com.example.notes_app.note_list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notes_app.R
import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.presentation.ui.theme.NotesAppTheme
import com.example.notes_app.core.presentation.util.TestTags
import com.example.notes_app.note_list.presentation.components.ListNoteItem

@Composable
fun NoteListScreen(
    onNavigateToAddNote: () -> Unit,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    previewNotes: List<NoteItem>? = null,
    previewOrderByTitle: Boolean? = null,
) {
    val noteListState by noteListViewModel.noteListState.collectAsState()
    val orderByTitleState by noteListViewModel.orderByTitleState.collectAsState()

    LaunchedEffect(true) {
        noteListViewModel.loadNotes()
    }

    NoteListContent(
        notesState = previewNotes ?: noteListState,
        isOrderedByTitleState = previewOrderByTitle ?: orderByTitleState,
        onNavigateToAddNote = onNavigateToAddNote,
        onChangeOrder = { noteListViewModel.changeOrder() },
        onDeleteNote = { note -> noteListViewModel.deleteNote(note) }
    )
}

@Composable
fun NoteListContent(
    notesState: List<NoteItem>,
    isOrderedByTitleState: Boolean,
    onNavigateToAddNote: () -> Unit,
    onChangeOrder: () -> Unit,
    onDeleteNote: (NoteItem) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.notes, notesState.size),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onChangeOrder() }
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = if (isOrderedByTitleState) stringResource(R.string.t)
                        else stringResource(R.string.d),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = if (isOrderedByTitleState) stringResource(R.string.sort_by_date)
                        else stringResource(R.string.sort_by_title)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .testTag(TestTags.ADD_NOTE_FAB),
                onClick = onNavigateToAddNote
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_a_note)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(
                count = notesState.size,
                key = { it }
            ) { index ->
                ListNoteItem(
                    onDelete = { onDeleteNote(notesState[index]) },
                    noteItem = notesState[index]
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNoteListScreen() {
    NotesAppTheme {
        val sampleNotes = listOf(
            NoteItem(
                id = 1,
                title = "Sample Note 1",
                description = "This is a description for the first sample note item.",
                imageUrl = "",
                dateAdded = 1
            ),
            NoteItem(
                id = 2,
                title = "Sample Note 2",
                description = "This is a description for the second sample note item.",
                imageUrl = "",
                dateAdded = 2
            )
        )

        NoteListContent(
            notesState = sampleNotes,
            isOrderedByTitleState = true,
            onNavigateToAddNote = { /* No-op for preview */ },
            onChangeOrder = { /* No-op for preview */ },
            onDeleteNote = { /* No-op for preview */ }
        )
    }
}
