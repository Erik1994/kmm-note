package com.example.kmmnote.android.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kmmnote.domain.note.NoteDataSource

abstract class BaseViewModel(
    protected val noteDataSource: NoteDataSource,
    protected val savedStateHandle: SavedStateHandle
): ViewModel() {
}