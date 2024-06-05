package com.orion.date.calendars

import com.orion.date.LanguageType
import kotlinx.datetime.TimeZone

interface KMPCalendar {
    val year: Int
    val month: Int
    val day: Int
    val hour: Int
    val minute: Int
    val second: Int
    val timeMillis: Long

    val isLeapYear: Boolean

    fun getDayOfTheWeek(timeZone: TimeZone): Int
    fun getDaysInThisMonth(): Int
    fun getDaysInThisYear(): Int
    fun getMonthNames(languageType: LanguageType): Array<String>
    fun getThisMonthName(languageType: LanguageType): String
    fun getTodayName(
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
        languageType: LanguageType
    ): String

    fun toMutable(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): MutableKMPCalendar

    fun get12FormatHour(): Int {
        return if (hour <= 12) hour else (hour - 12)
    }
}