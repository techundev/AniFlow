package com.techun.dev.aniflow.core.utils

fun String.readingTimeMinutes(): Int {
    val wordCount = this.trim().split("\\s+".toRegex()).size
    return maxOf(1, wordCount / 200)
}