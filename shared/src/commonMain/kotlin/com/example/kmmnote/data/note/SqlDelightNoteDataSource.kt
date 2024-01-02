package com.example.kmmnote.data.note

import com.example.kmmnote.database.NoteDataBase
import com.example.kmmnote.domain.note.Note
import com.example.kmmnote.domain.note.NoteDataSource
import com.example.kmmnote.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(
    db: NoteDataBase
): NoteDataSource {
    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHext = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}