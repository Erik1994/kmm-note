//
//  NoteViewModel.swift
//  iosApp
//
//  Created by Erik Antonyan on 15.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteScreen {
    @MainActor class NoteViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource? = nil
        
        private let searchNotesUseCase: SearchNoteUseCase = SearchNoteUseCaseImpl()
        private var notes = [Note]()
        
        @Published private(set) var filteredNotes = [Note]()
        
        @Published var searchText = "" {
            didSet {
                filteredNotes = searchNotesUseCase.invoke(notes: self.notes, query: searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?) {
            if id != nil {
                noteDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }

        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNouteDataSource(noteDataSource: NoteDataSource) {
            self.noteDataSource = noteDataSource
        }
    }
}
