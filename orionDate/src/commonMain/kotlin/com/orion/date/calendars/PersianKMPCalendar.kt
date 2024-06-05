package com.orion.date.calendars

import com.orion.date.CalendarType
import com.orion.date.LanguageType
import com.orion.date.dayOfWeek
import com.orion.date.getDayName
import com.orion.date.getIsLeapYear
import com.orion.date.getMonthNames
import com.orion.date.gregorianToPersian
import com.orion.date.persianToGregorian
import com.orion.date.toDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmOverloads

/**
 * month starts from 1
 * */
class PersianKMPCalendar : KMPCalendar, Comparable<PersianKMPCalendar> {

    override val year: Int
    override val month: Int
    override val day: Int
    override val hour: Int
    override val minute: Int
    override val second: Int
    override val timeMillis: Long

    override val isLeapYear: Boolean
        get() = getIsLeapYear(year, CalendarType.Persian)

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
        if (year < 1) {
            throw IllegalArgumentException("Year must be greater than 0")
        }
        this.year = year
        if (month < 1 || month > 12) {
            throw IllegalArgumentException("Month must be between 1 and 12")
        }
        this.month = month
        if (day < 1 || day > 31) {
            throw IllegalArgumentException("Day must be between 1 and 31")
        }
        this.day = day
        if (hour < 0 || hour > 23) {
            throw IllegalArgumentException("Hour must be between 0 and 23")
        }
        this.hour = hour
        if (minute < 0 || minute > 59) {
            throw IllegalArgumentException("Minute must be between 0 and 59")
        }
        this.minute = minute
        if (second < 0 || second > 59) {
            throw IllegalArgumentException("Second must be between 0 and 59")
        }
        this.second = second

        if (day > getDaysInThisMonth()) {
            throw IllegalArgumentException(
                " Day in the ${getThisMonthName(LanguageType.English)} must be between 1 and ${getDaysInThisMonth()}"
            )
        }

        timeMillis = persianToGregorian(
            year,
            month,
            day,
            hour,
            minute,
            second,
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
    }

    @JvmOverloads
    constructor(
        timezone: TimeZone = TimeZone.currentSystemDefault()
    ) : this(
        milliseconds = Clock.System.now().toEpochMilliseconds(),
        timezone = timezone
    )

    /**
     * starts of from 1*/
    override fun getDayOfTheWeek(timeZone: TimeZone): Int {
        return dayOfWeek(timeZone) + 1
    }

    override fun getDaysInThisMonth(): Int {
        if (month == 12 && !getIsLeapYear(year = year, CalendarType.Persian)) {
            return 29
        }
        return if (month <= 6) {
            31
        } else {
            30
        }
    }

    override fun getDaysInThisYear(): Int {
        return if (
            getIsLeapYear(
                year = year,
                calendarType = CalendarType.Persian
            )
        ) {
            366
        } else 365
    }

    override fun getMonthNames(languageType: LanguageType): Array<String> {
        return getMonthNames(CalendarType.Persian, languageType)
    }

    override fun getThisMonthName(languageType: LanguageType): String {
        return getMonthNames(
            calendarType = CalendarType.Persian,
            languageType = languageType
        )[month - 1]
    }

    override fun getTodayName(
        timeZone: TimeZone,
        languageType: LanguageType
    ): String {
        return getDayName(
            day = dayOfWeek(timeZone = timeZone) + 1,
            languageType = languageType
        )
    }

    fun getTimeOfTheDay(shortVersion: Boolean): String {
        return if (shortVersion) {
            if (hour <= 12) AM_SHORT_NAME else PM_SHORT_NAME
        } else {
            if (hour <= 12) AM_NAME else PM_NAME
        }
    }

    override fun toMutable(timeZone: TimeZone): PersianMutableKMPCalendar {
        return PersianMutableKMPCalendar(
            timeMillis = timeMillis,
            timeZone = timeZone
        )
    }

    override fun compareTo(other: PersianKMPCalendar): Int {
        return this.timeMillis.compareTo(other.timeMillis)
    }

    override fun equals(other: Any?): Boolean {
        val kmpCalendar = other as? PersianKMPCalendar
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

    companion object {
        const val AM_SHORT_NAME: String = "ق.ظ"
        const val PM_SHORT_NAME: String = "ب.ظ"
        const val AM_NAME: String = "قبل از ظهر"
        const val PM_NAME: String = "بعد از ظهر"
    }
}
