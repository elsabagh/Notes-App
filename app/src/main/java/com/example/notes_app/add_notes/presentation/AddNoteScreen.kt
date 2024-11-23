package com.example.notes_app.add_notes.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notes_app.R
import com.example.notes_app.add_notes.presentation.components.ImagesDialogContent
import com.example.notes_app.core.presentation.ui.theme.NotesAppTheme
import com.example.notes_app.core.presentation.util.TestTags
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNoteScreen(
    onSave: () -> Unit,
    addNoteViewModel: AddNoteViewModel = hiltViewModel(),
) {

    val addNoteState by addNoteViewModel.addNoteState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(true) {
        addNoteViewModel.noteSavedFlow.collectLatest { saved ->
            if (saved) {
                onSave()
            } else {
                Toast.makeText(
                    context,
                    R.string.error_saving_note,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    AddNoteContent(
        addNoteState = addNoteState,
        onUpdateTitle = { newTitle -> addNoteViewModel.onAction(AddNoteActions.UpdateTitle(newTitle)) },
        onUpdateDescription = { newDescription ->
            addNoteViewModel.onAction(
                AddNoteActions.UpdateDescription(
                    newDescription
                )
            )
        },
        onUpdateImageQuery = { newQuery ->
            addNoteViewModel.onAction(
                AddNoteActions.UpdateSearchImageQuery(
                    newQuery
                )
            )
        },
        onPickImage = { addNoteViewModel.onAction(AddNoteActions.PickImage(it)) },
        onSaveNote = { addNoteViewModel.onAction(AddNoteActions.SaveNote) },
        onToggleImagesDialog = { addNoteViewModel.onAction(AddNoteActions.UpdateImagesDialogVisibility) }
    )
}

@Composable
fun AddNoteContent(
    addNoteState: AddNoteState,
    onUpdateTitle: (String) -> Unit,
    onUpdateDescription: (String) -> Unit,
    onUpdateImageQuery: (String) -> Unit,
    onPickImage: (String) -> Unit,
    onSaveNote: () -> Unit,
    onToggleImagesDialog: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    onToggleImagesDialog()
                }
                .testTag(
                    TestTags.NOTE_IMAGE
                ),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(addNoteState.imageURL)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = addNoteState.searchImagesQuery,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.TITLE_TEXT_FIELD),
            value = addNoteState.title,
            onValueChange = {
                onUpdateTitle(it)
            },
            label = {
                Text(text = stringResource(R.string.title))
            },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.DESCRIPTION_TEXT_FIELD),
            value = addNoteState.description,
            onValueChange = {
                onUpdateDescription(
                    it
                )
            },
            label = {
                Text(text = stringResource(R.string.description))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.SAVE_BUTTON),
            onClick = {
                onSaveNote()
            }
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (addNoteState.isImagesDialogShowing) {
        Dialog(
            onDismissRequest = {
                onToggleImagesDialog()
            }
        ) {
            ImagesDialogContent(
                addNoteState = addNoteState,
                onSearchQueryChange = {
                    onUpdateImageQuery(it)
                },
                onImageClick = {
                    onPickImage(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)

@Composable
fun PreviewAddNoteScreen() {
    NotesAppTheme {
        val mockState = AddNoteState(
            title = "Sample Title",
            description = "This is a sample description for the note.",
            imageURL = "https://sampleimage.com/sample.jpg",
            searchImagesQuery = "Search Image",
            isImagesDialogShowing = false,
            imageList = emptyList(),
            isLoadingImages = false
        )

        AddNoteContent(
            addNoteState = mockState,
            onUpdateTitle = { },
            onUpdateDescription = { },
            onUpdateImageQuery = { },
            onPickImage = { },
            onSaveNote = { },
            onToggleImagesDialog = { }
        )
    }
}

