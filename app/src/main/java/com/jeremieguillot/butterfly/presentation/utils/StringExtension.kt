package com.jeremieguillot.butterfly.presentation.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DATE_FORMAT_PATTERN_SERVER = "yyyy-MM-dd HH:mm:ss.SSS'Z'"

fun String.toDate(): Date {
    return try {
        return SimpleDateFormat(DATE_FORMAT_PATTERN_SERVER, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(this) ?: Date()
    } catch (e: Exception) {
        Timber.e(e)
        Date()
    }
}