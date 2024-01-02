package com.example.kmmnote.domain.note

import com.example.kmmnote.presentation.BabyBlueHex
import com.example.kmmnote.presentation.LightGreenHex
import com.example.kmmnote.presentation.RedOrangeHex
import com.example.kmmnote.presentation.RedPinkHex
import com.example.kmmnote.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, BabyBlueHex, LightGreenHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}
