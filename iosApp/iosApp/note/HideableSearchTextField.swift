//
//  HideableSearchTextField.swift
//  iosApp
//
//  Created by Erik Antonyan on 15.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct HideableSearchTextField<Destination: View>: View {
    
    var onSearchToggled: () -> Void
    var destinationProvider: () -> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                Spacer()
            }
            Button(action: onSearchToggled) {
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
                    .foregroundColor(.white)
            }
            NavigationLink(destination: destinationProvider()) {
                Image(systemName: "plus")
                    .foregroundColor(.white)
            }
        }
    }
}

//#Preview {
//    HideableSearchTextField()
//}
