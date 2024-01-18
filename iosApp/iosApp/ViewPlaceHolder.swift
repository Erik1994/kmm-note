//
//  ViewPlaceHolder.swift
//  iosApp
//
//  Created by Erik Antonyan on 18.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


extension View {
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content) -> some View {

        ZStack(alignment: alignment) {
            placeholder().opacity(shouldShow ? 1 : 0)
            self
        }
    }
}
