package com.example.kmmnote.android.di

import android.app.Application
import com.example.kmmnote.data.local.DatabaseDriverFactory
import com.example.kmmnote.data.note.SqlDelightNoteDataSource
import com.example.kmmnote.database.NoteDataBase
import com.example.kmmnote.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDb(sqlDriver: SqlDriver): NoteDataBase {
        return NoteDataBase(driver = sqlDriver)
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(noteDataBase: NoteDataBase): NoteDataSource {
        return SqlDelightNoteDataSource(noteDataBase)
    }

}