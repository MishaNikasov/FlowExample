package com.app.flowexample.common.extensions

import android.content.Context
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

const val PATTERN_SERVER = "yyyy-MM-dd"
const val DD_MMMM = "dd MMMM"
const val FULL_DATE = "EEE, d MMM, yyyy"
const val DEFAULT_DATE = "MMM dd, yyyy"

fun LocalDate?.byPattern(pattern: String): String {
    if (this == null) {
        return ""
    }
    val formattedDate: String

    val simpleDateFormat = DateTimeFormatter.ofPattern(pattern)
    formattedDate = simpleDateFormat.format(this)

    return formattedDate
}

fun LocalDate.getServerFormatTime(): String {
    return byPattern(PATTERN_SERVER)
}

fun String?.getDateFromServerTime(): LocalDate? {
    var date: LocalDate? = null
    if (this == null)
        return null
    try {
        val formatter = DateTimeFormatter.ofPattern(PATTERN_SERVER, Locale.getDefault())
        formatter.withZone(ZoneId.of("UTC"))
        val parsed: TemporalAccessor = formatter.parse(this)
        date = LocalDate.from(parsed)
    } catch (e: Exception) {
    }
    return date
}

fun LocalDate?.getAdaptiveDay(context: Context, pattern: String = DD_MMMM) =
    when {
        this == null -> ""
        isToday() -> "Today"
        isYesterday() -> "Yesterday"
        else -> byPattern(pattern)
    }

fun LocalDate.isToday() = getToday().byPattern(DEFAULT_DATE) == byPattern(DEFAULT_DATE)

fun LocalDate.isYesterday() = getYesterday().byPattern(DEFAULT_DATE) == byPattern(DEFAULT_DATE)

fun getToday(): LocalDate {
    return LocalDate.now()
}

fun LocalDate.isInFuture(): Boolean {
    return this.isBefore(getToday())
}

fun getTomorrow(): LocalDate = LocalDate.now().plusDays(1)

fun getYesterday(): LocalDate = LocalDate.now().plusDays(-1)
