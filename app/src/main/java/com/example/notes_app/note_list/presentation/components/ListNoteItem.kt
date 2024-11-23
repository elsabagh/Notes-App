package com.example.notes_app.note_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notes_app.core.domain.model.NoteItem
import com.example.notes_app.core.presentation.util.TestTags


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
            model = ImageRequest
                .Builder(LocalContext.current)
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
