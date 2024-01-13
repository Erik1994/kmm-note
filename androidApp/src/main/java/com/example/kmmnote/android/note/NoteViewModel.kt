package com.example.kmmnote.android.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmnote.android.common.BaseViewModel
import com.example.kmmnote.domain.note.Note
import com.example.kmmnote.domain.note.NoteDataSource
import com.example.kmmnote.domain.note.usecase.SearchNoteUseCase
import com.example.kmmnote.domain.note.usecase.SearchNoteUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    noteDataSource: NoteDataSource,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(noteDataSource, savedStateHandle) {
    private val searchNoteUseCase: SearchNoteUseCase = SearchNoteUseCaseImpl()

    private val notes = savedStateHandle.getStateFlow(NOTES_KEY, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SEARCH_TEXT_KEY, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IS_SEARCH_ACTIVE_KEY, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteState(
            notes = searchNoteUseCase(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), NoteState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTES_KEY] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle[SEARCH_TEXT_KEY] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IS_SEARCH_ACTIVE_KEY] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle[SEARCH_TEXT_KEY] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }


    private companion object {
        const val NOTES_KEY = "notes_key"
        const val SEARCH_TEXT_KEY = "search_text_key"
        const val IS_SEARCH_ACTIVE_KEY = "is_search_active_key"
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}