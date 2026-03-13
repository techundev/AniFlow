package com.techun.dev.aniflow.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toReadableDate(): String {
    if (this.isBlank()) return ""

    val inputFormats = listOf(
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH),
        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH),
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)
    )

    val outputFormat = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.ENGLISH)

    for (format in inputFormats) {
        try {
            val date = format.parse(this.trim())
            if (date != null) return outputFormat.format(date)
        } catch (e: Exception) {
            continue
        }
    }

    return this
}