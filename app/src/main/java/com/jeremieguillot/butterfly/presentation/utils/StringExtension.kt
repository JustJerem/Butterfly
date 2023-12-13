package com.jeremieguillot.butterfly.presentation.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DATE_FORMAT_PATTERN_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun String.toDate(): Date {
    return try {
//        // Remove seconds faction from the dateString if present
//        // eg: ("2023-05-23T09:26:54.899317Z") -> ("2023-05-23T09:26:54Z")
//        val pattern = "\\.\\d*Z".toRegex()
//        val containsPattern = pattern.containsMatchIn(this)
//        val dateStringWithoutFraction = if (containsPattern) {
//            this.substringBeforeLast('.') + "Z"
//        } else {
//            this
//        }
        return SimpleDateFormat(DATE_FORMAT_PATTERN_SERVER, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(this) ?: Date()
    } catch (e: Exception) {
        Timber.e(e)
        Date()
    }
}

//fun String.toGMTDate(): Date {
//    return try {
//        return SimpleDateFormat(GMT_DATE_FORMAT, Locale.getDefault()).apply {
//            timeZone = TimeZone.getTimeZone("GMT")
//        }.parse(this) ?: Date()
//    } catch (e: Exception) {
//        Timber.e(e)
//        Date()
//    }
//}