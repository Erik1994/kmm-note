package com.example.kmmnote.di

import com.example.kmmnote.data.local.DatabaseDriverFactory
import com.example.kmmnote.data.note.SqlDelightNoteDataSource
import com.example.kmmnote.database.NoteDataBase
import com.example.kmmnote.domain.note.NoteDataSource

class DatabaseModule {
    private val factory by lazy {
        DatabaseDriverFactory()
    }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDataBase(factory.createDriver()))
    }
}