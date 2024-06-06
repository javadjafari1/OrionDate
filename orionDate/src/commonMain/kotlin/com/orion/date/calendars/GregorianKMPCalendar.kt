package com.orion.date.calendars

import com.orion.date.getThisMonthName
import com.orion.date.language.EnglishGregorianLanguage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmOverloads

/**
 * month starts from 1
 * */
open class GregorianKMPCalendar : KMPCalendar {

    final override val year: Int
    final override val month: Int
    final override val day: Int
    final override val hour: Int
    final override val minute: Int
    final override val second: Int
    override val timeMillis: Long
    final override val timeZone: TimeZone

    @JvmOverloads
    constructor(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ) {
        require(year > 0) { "Year must be greater than 0" }
        require(month in 1..12) { "Month must be between 1 and 12" }
        require(day in 1..31) { "Day must be between 1 and 31" }
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }
        require(second in 0..59) { "Second must be between 0 and 59" }
        require(day <= getDaysInThisMonth()) {
            buildString {
                append("Day in the ")
                append(getThisMonthName(EnglishGregorianLanguage))
                append(" must be between 1 and ")
                append(getDaysInThisMonth())
            }
        }

        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
        this.timeZone = timeZone

        timeMillis = LocalDateTime(
            year = year,
            monthNumber = month,
            dayOfMonth = day,
            hour = hour,
            minute = minute,
            second = second,
        ).toInstant(timeZone = timeZone)
            .toEpochMilliseconds()
    }

    @JvmOverloads
    constructor(
        milliseconds: Long,
        timezone: TimeZone = TimeZone.currentSystemDefault()
    ) {
        val dateTime = Instant.fromEpochMilliseconds(milliseconds)
            .toLocalDateTime(timezone)

        year = dateTime.year
        month = dateTime.monthNumber
        day = dateTime.dayOfMonth
        hour = dateTime.hour
        minute = dateTime.minute
        second = dateTime.second
        this.timeMillis = milliseconds
        this.timeZone = timezone
    }

    private constructor(
        timezone: TimeZone = TimeZone.currentSystemDefault()
    ) : this(
        milliseconds = Clock.System.now().toEpochMilliseconds(),
        timezone = timezone
    )

    override fun getDaysInThisMonth(): Int {
        return when (month) {
            2 -> if (isLeapYear()) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    override fun getDaysInThisYear(): Int {
        return if (isLeapYear()) {
            366
        } else 365
    }

    override fun toMutable(): GregorianMutableKMPCalendar {
        return GregorianMutableKMPCalendar(
            timeMillis = timeMillis,
            timeZone = timeZone
        )
    }

    override fun isLeapYear(): Boolean {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0
            }
            return true
        }
        return false
    }

    //TODO rename
    fun getTimeOfTheDay(): String {
        return if (hour <= 12) AM_NAME else PM_NAME
    }

    companion object {
        private const val AM_NAME: String = "AM"
        private const val PM_NAME: String = "PM"

        fun now(): GregorianKMPCalendar {
            return GregorianKMPCalendar()
        }
    }
}
