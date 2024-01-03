package com.example.kmmnote.android.note

import com.example.kmmnote.domain.note.Note

data class NoteState (
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)