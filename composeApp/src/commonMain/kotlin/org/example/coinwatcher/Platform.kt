package org.example.coinwatcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform