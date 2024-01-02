package com.example.kmmnote.domain.note.usecase

import com.example.kmmnote.domain.note.Note
import com.example.kmmnote.domain.time.DateTimeUtil

class SearchNoteUseCaseImpl : SearchNoteUseCase {
    override fun invoke(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) return notes
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}