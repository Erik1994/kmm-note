package com.example.kmmnote.data.local

import android.content.Context
import com.example.kmmnote.database.NoteDataBase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(NoteDataBase.Schema, context, "note.db")
    }
}