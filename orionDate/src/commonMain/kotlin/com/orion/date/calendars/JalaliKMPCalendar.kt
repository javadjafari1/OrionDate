package com.orion.date.calendars

import com.orion.date.getThisMonthName
import com.orion.date.gregorianToPersian
import com.orion.date.language.PersianJalaliLanguage
import com.orion.date.persianToGregorian
import com.orion.date.toDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmOverloads
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/**
 * month starts from 1
 * */
open class JalaliKMPCalendar : KMPCalendar {

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
                append(getThisMonthName(PersianJalaliLanguage))
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

        timeMillis = persianToGregorian(
            year = year,
            month = month,
            day = day,
            hour = hour,
            minute = minute,
            second = second,
        ).toDate()
            .toInstant(timeZone)
            .toEpochMilliseconds()
    }

    @JvmOverloads
    constructor(
        milliseconds: Long,
        timezone: TimeZone = TimeZone.currentSystemDefault()
    ) {
        val dateTime = Instant.fromEpochMilliseconds(milliseconds)
            .toLocalDateTime(timezone)
        val s = gregorianToPersian(
            year = dateTime.year,
            month = dateTime.monthNumber,
            day = dateTime.dayOfMonth,
            hour = dateTime.hour,
            minute = dateTime.minute,
            second = dateTime.second,
        )
        year = s.year
        month = s.month
        day = s.day
        hour = s.hour
        minute = s.minute
        second = s.second
        timeMillis = milliseconds
        this.timeZone = timezone
    }

    private constructor(
        timezone: TimeZone = TimeZone.currentSystemDefault()
    ) : this(
        milliseconds = Clock.System.now().toEpochMilliseconds(),
        timezone = timezone
    )

    override fun getDaysInThisMonth(): Int {
        if (month == 12 && !isLeapYear()) {
            return 29
        }
        return if (month <= 6) {
            31
        } else {
            30
        }
    }

    override fun getDaysInThisYear(): Int {
        return if (isLeapYear()) {
            366
        } else 365
    }

    fun getTimeOfTheDay(shortVersion: Boolean): String {
        return if (shortVersion) {
            if (hour <= 12) AM_SHORT_NAME else PM_SHORT_NAME
        } else {
            if (hour <= 12) AM_NAME else PM_NAME
        }
    }

    override fun toMutable(): JalaliMutableKMPCalendar {
        return JalaliMutableKMPCalendar(
            timeMillis = timeMillis,
            timeZone = timeZone
        )
    }

    override fun isLeapYear(): Boolean {
        val referenceYear = 1375.0
        var startYear = 1375.0
        val yearRes: Double = year - referenceYear
        //first of all make sure year is not multiplier of 1375
        if (yearRes == 0.0 || yearRes % 33 == 0.0) {
            return true //year is 1375 or 1375+-(i)*33
        }

        if (yearRes > 0) {
            if (yearRes > 33) {
                val numb: Double = yearRes / 33
                startYear = referenceYear + (floor(numb) * 33)
            }
        } else {
            if (yearRes > -33) {
                startYear = referenceYear - 33
            } else {
                val numb: Double = abs(yearRes / 33)
                startYear = referenceYear - (ceil(numb) * 33)
            }
        }
        val leapYears = listOf(
            startYear, startYear + 4, startYear + 8, startYear + 12, startYear + 16,
            startYear + 20,
            startYear + 24, startYear + 28, startYear + 33
        )

        return leapYears.binarySearch(year.toDouble()) >= 0
    }

    companion object {
        private const val AM_SHORT_NAME: String = "ق.ظ"
        private const val PM_SHORT_NAME: String = "ب.ظ"
        private const val AM_NAME: String = "قبل از ظهر"
        private const val PM_NAME: String = "بعد از ظهر"

        fun now(): JalaliKMPCalendar {
            return JalaliKMPCalendar()
        }
    }
}
