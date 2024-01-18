//
//  NoteScreen.swift
//  iosApp
//
//  Created by Erik Antonyan on 15.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteScreen: View {
    private var noteDataSource: NoteDataSource
    @StateObject var noteViewModel = NoteViewModel(noteDataSource: nil)
    

    @State private var isNoteSelcted = false
    @State var selectedNoteId: Int64? = nil
    
    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                NavigationLink(destination: NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: selectedNoteId), isActive: $isNoteSelcted){
                    EmptyView()
                }.hidden()
                HideableSearchTextField<NoteDetailScreen>(onSearchToggled: {
                    noteViewModel.toggleIsSearchActive()
                }, destinationProvider: {
                    NoteDetailScreen(noteDataSource: noteDataSource, noteId: selectedNoteId)
                }, isSearchActive: noteViewModel.isSearchActive, searchText: $noteViewModel.searchText)
                .frame(maxWidth: .infinity, minHeight: 40)
                .padding()
                
                if !noteViewModel.isSearchActive {
                    Text("All notes")
                        .font(.title2)
                }
                
                List {
                    ForEach(noteViewModel.filteredNotes, id: \.self.id) { note in
                        Button(action:{
                            isNoteSelcted = true
                            selectedNoteId = note.id?.int64Value
                        }) {
                            NoteItem(note: note, onDeleteClick: {
                                noteViewModel.deleteNoteById(id: note.id?.int64Value)
                            })
                        }
                    }
                }.onAppear {
                    noteViewModel.loadNotes()
                }
                .listStyle(.plain)
                .listRowSeparator(.hidden)
            }
        }.onAppear {
            noteViewModel.setNouteDataSource(noteDataSource: noteDataSource)
        }
    }
}

//#Preview {
//    NoteScreen()
//}
