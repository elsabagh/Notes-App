package com.example.notes_app.note_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notes_app.R
import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.presentation.util.TestTags

@Composable
fun NoteListScreen(
    onNavigateToAddNote: () -> Unit,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    previewNotes: List<NoteItem>? = null,
    previewOrderByTitle: Boolean? = null,
) {
    val noteListState by noteListViewModel.noteListState.collectAsState()
    val orderByTitleState by noteListViewModel.orderByTitleState.collectAsState()

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
                modifier = Modifier.testTag(TestTags.ADD_NOTE_FAB),
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


@Composable
fun ListNoteItem(
    onDelete: () -> Unit,
    noteItem: NoteItem,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            model = ImageRequest.Builder(LocalContext.current)
                .data(noteItem.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = noteItem.title,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {

            Text(
                text = noteItem.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = noteItem.description,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

        Icon(
            modifier = Modifier
                .clickable { onDelete() },
            imageVector = Icons.Default.Clear,
            contentDescription = TestTags.DELETE_NOTE + noteItem.title,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteListScreen() {
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

@Preview(showBackground = true)
@Composable
fun ListNoteItemPreview() {
    val sampleNote = NoteItem(
        id = 1,
        title = "Sample Note",
        description = "This is a description for the sample note item.",
        imageUrl = "",
        dateAdded = 1
    )

    ListNoteItem(
        onDelete = { /* No-op for preview */ },
        noteItem = sampleNote
    )
}
