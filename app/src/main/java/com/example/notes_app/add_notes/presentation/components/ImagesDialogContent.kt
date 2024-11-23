package com.example.notes_app.add_notes.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notes_app.R
import com.example.notes_app.add_notes.presentation.AddNoteState
import com.example.notes_app.core.presentation.util.TestTags

@Composable
fun ImagesDialogContent(
    addNoteState: AddNoteState,
    onSearchQueryChange: (String) -> Unit,
    onImageClick: (String) -> Unit,
) {

    Log.d("ImagesDialogContent", "ImagesDialogContent")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.background)
            .testTag(TestTags.SEARCH_IMAGE_DIALOG),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.SEARCH_IMAGE_TEXT_FIELD),
            value = addNoteState.searchImagesQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            label = {
                Text(text = stringResource(R.string.search_image))
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (addNoteState.isLoadingImages) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {

                itemsIndexed(addNoteState.imageList) { index, url ->
                    Log.d("ImagesDialogContent", ": $url")
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { onImageClick(url) }
                            .testTag(TestTags.PICKED_IMAGE + url),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = stringResource(R.string.image),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewImagesDialogContent() {
    // Simulating the AddNoteState with a sample search query and images list
    val addNoteState = AddNoteState(
        searchImagesQuery = "search image",
        isLoadingImages = false, // Set to true if you want to preview the loading state
        imageList = listOf(
            "https://www.example.com/image1.jpg",
            "https://www.example.com/image2.jpg",
            "https://www.example.com/image3.jpg",
            "https://www.example.com/image3.jpg"
        )
    )


    ImagesDialogContent(
        addNoteState = addNoteState,
        onSearchQueryChange = { query -> Log.d("SearchQuery", query) },
        onImageClick = { url -> Log.d("ImageClicked", url) }
    )
}

