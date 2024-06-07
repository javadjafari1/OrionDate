package com.orion.date.calendars

import kotlinx.datetime.TimeZone

abstract class KMPCalendar : Comparable<KMPCalendar> {
    abstract val year: Int
    abstract val month: Int
    abstract val day: Int
    abstract val hour: Int
    abstract val minute: Int
    abstract val second: Int
    abstract val timeMillis: Long
    abstract val timeZone: TimeZone

    abstract fun getDaysInThisMonth(): Int
    abstract fun getDaysInThisYear(): Int
    abstract fun isLeapYear(): Boolean

    fun get12FormatHour(): Int {
        return if (hour <= 12) hour else (hour - 12)
    }

    override fun compareTo(other: KMPCalendar): Int {
        return timeMillis.compareTo(other.timeMillis)
    }

    override fun equals(other: Any?): Boolean {
        val kmpCalendar = other as? KMPCalendar
        return if (kmpCalendar != null) {
            timeMillis == kmpCalendar.timeMillis
        } else false
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + timeMillis.hashCode()
        return result
    }

    fun isSameYearAs(kmpCalendar: KMPCalendar): Boolean {
        return kmpCalendar.year == year
    }

    fun isSameMonthAs(kmpCalendar: KMPCalendar): Boolean {
        return isSameYearAs(kmpCalendar) && kmpCalendar.month == month
    }

    fun isSameDayAs(kmpCalendar: KMPCalendar): Boolean {
        return isSameYearAs(kmpCalendar) && isSameMonthAs(kmpCalendar) && kmpCalendar.day == day
    }

    abstract fun toMutable(): MutableKMPCalendar
}