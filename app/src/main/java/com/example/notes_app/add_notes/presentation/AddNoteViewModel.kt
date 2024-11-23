package com.example.notes_app.add_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_app.add_notes.domain.use_case.SearchImages
import com.example.notes_app.add_notes.domain.use_case.UpsertNote
import com.example.notes_app.add_notes.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    val upsertNote: UpsertNote,
    private val searchImages: SearchImages,

    ) : ViewModel() {
    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSaveChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSaveChannel.receiveAsFlow()

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
                _addNoteState.update {
                    it.copy(searchImagesQuery = action.newQuery)
                }
                searchImages(action.newQuery)
            }

            is AddNoteActions.PickImage -> {
                _addNoteState.update {
                    it.copy(imageURL = action.imageUrl)
                }
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                _addNoteState.update {
                    it.copy(isImagesDialogShowing = !it.isImagesDialogShowing)
                }
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

    private var searchJop: Job? = null

    fun searchImages(query: String) {
        searchJop?.cancel()
        searchJop = viewModelScope.launch {
            delay(500)

            searchImages.invoke(query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _addNoteState.update {
                                it.copy(imageList = emptyList())
                            }
                        }

                        is Resource.Loading -> {
                            _addNoteState.update {
                                it.copy(isLoadingImages = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            _addNoteState.update {
                                it.copy(imageList = result.data?.images ?: emptyList())
                            }
                        }
                    }
                }
        }
    }
}