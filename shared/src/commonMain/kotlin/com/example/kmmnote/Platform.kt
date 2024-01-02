package com.example.kmmnote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform