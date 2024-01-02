package com.example.kmmnote.data.note

import com.example.kmmnote.domain.note.Note
import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        colorHex = colorHext,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}