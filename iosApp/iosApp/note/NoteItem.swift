//
//  NoteItem.swift
//  iosApp
//
//  Created by Erik Antonyan on 15.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note: Note
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                    .foregroundColor(.black)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, 3)
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom, 3)
                .foregroundColor(.black)
            HStack {
                Spacer()
                Text(DateTimeUtil().formatNoteData(dateTime: note.created))
                    .font(.footnote)
                    .fontWeight(.light)
                    .foregroundColor(.black)
            }
        }
        .padding()
        .background(Color(hex: note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

//#Preview {
//    NoteItem()
//}
