package com.example.kmmnote.android.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.kmmnote.android.common.BaseViewModel
import com.example.kmmnote.android.navigation.Route
import com.example.kmmnote.domain.note.Note
import com.example.kmmnote.domain.note.NoteDataSource
import com.example.kmmnote.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    noteDataSource: NoteDataSource,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(noteDataSource, savedStateHandle) {

    private val noteTitle = savedStateHandle.getStateFlow(NOTE_TITLE_KEY, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(TITLE_TEXT_FOCUSED_KEY, false)
    private val noteContent = savedStateHandle.getStateFlow(NOTE_CONTENT_KEY, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(
        CONTENT_TEXT_FOCUSED_KEY, false
    )
    private val noteColor =
        savedStateHandle.getStateFlow(NOTE_COLOR_KEY, Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        NoteDetailState()
    )

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()
    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>(Route.NOTE_ID)?.let {
            if (it == -1L) return@let
            existingNoteId = it
            viewModelScope.launch {
                noteDataSource.getNoteById(it)?.run {
                    savedStateHandle[NOTE_TITLE_KEY] = noteTitle
                    savedStateHandle[NOTE_CONTENT_KEY] = noteContent
                    savedStateHandle[TITLE_TEXT_FOCUSED_KEY] = isNoteTitleFocused
                }
            }
        }
    }

    fun onNoteTitleChanged(title: String) {
        savedStateHandle[NOTE_TITLE_KEY] = title
    }

    fun onNoteContentChanged(content: String) {
        savedStateHandle[CONTENT_TEXT_FOCUSED_KEY] = content
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle[TITLE_TEXT_FOCUSED_KEY] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle[CONTENT_TEXT_FOCUSED_KEY] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    private companion object {
        const val NOTE_TITLE_KEY = "note_title_key"
        const val TITLE_TEXT_FOCUSED_KEY = "title_text_focused_key"
        const val CONTENT_TEXT_FOCUSED_KEY = "content_text_focused_key"
        const val NOTE_CONTENT_KEY = "note_content_key"
        const val NOTE_COLOR_KEY = "note_color_key"
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}