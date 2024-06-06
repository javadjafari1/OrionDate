package com.orion.date

import com.orion.date.calendars.GregorianKMPCalendar
import com.orion.date.calendars.JalaliKMPCalendar
import com.orion.date.calendars.KMPCalendar
import com.orion.date.language.CalendarLanguage
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun KMPCalendar.toDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(timeMillis)
    return instant.toLocalDateTime(timeZone)
}

fun LocalDateTime.toJalaliKMPCalendar(timeZone: TimeZone): JalaliKMPCalendar {
    val timeMillis = toInstant(timeZone).toEpochMilliseconds()
    return JalaliKMPCalendar(timeMillis)
}

fun LocalDateTime.toGregorianKMPCalendar(timeZone: TimeZone): GregorianKMPCalendar {
    val timeMillis = toInstant(timeZone).toEpochMilliseconds()
    return GregorianKMPCalendar(timeMillis)
}

fun KMPCalendar.getThisMonthName(calendarLanguage: CalendarLanguage): String {
    return calendarLanguage.monthNames[month - 1]
}

fun KMPCalendar.getTodayName(calendarLanguage: CalendarLanguage): String {
    return calendarLanguage.weekDayNames[day - 1]
}
