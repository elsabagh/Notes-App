package com.example.notes_app.add_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes_app.add_notes.domain.use_case.UpsertNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    val upsertNote: UpsertNote,
) : ViewModel() {
    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSaveChannel = Channel<Boolean>()
    val noteSaveFlow = _noteSaveChannel.receiveAsFlow()

    fun onAction(action: AddNoteActions) {
        when (action) {
            is AddNoteActions.UpdateTitle -> {
                _addNoteState.update {
                    it.copy(title = action.newTitle)
                }
            }

            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update {
                    it.copy(description = action.newDescription)
                }
            }

            is AddNoteActions.UpdateSearchImageQuery -> {
                TODO()
            }

            is AddNoteActions.PickImage -> {
                TODO()
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                TODO()
            }

            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = upsertNote(
                        title = addNoteState.value.title,
                        description = addNoteState.value.description,
                        imageUrl = addNoteState.value.imageURL,
                    )
                    _noteSaveChannel.send(isSaved)
                }
            }

        }
        suspend fun upsertNote(
            title: String,
            description: String,
            imagesUrl: String,
        ): Boolean {
            return upsertNote(
                title = title,
                description = description,
                imagesUrl = imagesUrl
            )
        }
    }
}