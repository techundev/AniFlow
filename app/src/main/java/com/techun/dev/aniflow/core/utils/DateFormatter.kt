package com.techun.dev.aniflow.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toReadableDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: return this)
    } catch (e: Exception) {
        this
    }
}