package com.example.kmmnote.android.notedetail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kmmnote.android.R
import com.example.kmmnote.android.helper.UiText
import com.example.kmmnote.android.note.HideableSearchTextField
import com.example.kmmnote.android.note.NoteItem
import kotlinx.coroutines.flow.collect

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState(initial = UiText.DynamicString(""))

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) {
            onNavigateBack()
        }
    }

    LaunchedEffect(key1 = errorMessage) {
        val message = errorMessage.asString(context)
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = stringResource(R.string.enter_a_title),
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChanged = viewModel::onNoteTitleChanged,
                onFocusChanged = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = stringResource(R.string.enter_a_content),
                isHintVisible = state.isNoteContentHintVisible,
                onValueChanged = viewModel::onNoteContentChanged,
                onFocusChanged = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}