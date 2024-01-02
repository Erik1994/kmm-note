package com.example.kmmnote.domain.note.usecase

import com.example.kmmnote.domain.note.Note

interface SearchNoteUseCase {
    operator fun invoke(notes: List<Note>, query: String): List<Note>
}