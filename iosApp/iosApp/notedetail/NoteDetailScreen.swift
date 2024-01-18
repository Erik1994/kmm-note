//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by Erik Antonyan on 15.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    private var noteDataSource: NoteDataSource
    private var noteId: Int64? = nil
    
    @StateObject var viewModel = NoteDetailVewModel(noteDataSource: nil)
    @Environment(\.presentationMode) var presentation
    
    init(noteDataSource: NoteDataSource, noteId: Int64? = nil) {
        self.noteDataSource = noteDataSource
        self.noteId = noteId
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("", text: $viewModel.noteTitle)
                .font(.title)
                .foregroundColor(.black)
                .placeholder(when: viewModel.noteTitle.isEmpty, placeholder: {
                    Text("Enter a title...").foregroundColor(.black)
                })
            TextField("Enter a content...", text: $viewModel.noteContent)
                .foregroundColor(.black)
                .placeholder(when: viewModel.noteContent.isEmpty, placeholder: {
                    Text("Enter a content...").foregroundColor(.black)
                })
            Spacer()
        }.toolbar(content: {
            Button(action: {
                viewModel.saveNote {
                    self.presentation.wrappedValue.dismiss()
                }
            }) {
                Image(systemName: "checkmark")
            }
        })
        .padding()
        .background(Color(hex: viewModel.noteColor))
        .onAppear {
            viewModel.setParamsAndLoadNote(noteDataSource: noteDataSource, noteId: noteId)
        }
    }
}

//#Preview {
//    NoteDetailScreen()
//}
