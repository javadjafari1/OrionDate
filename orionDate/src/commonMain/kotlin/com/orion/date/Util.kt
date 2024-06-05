package com.orion.date

import com.orion.date.calendars.GregorianKMPCalendar
import com.orion.date.calendars.KMPCalendar
import com.orion.date.calendars.PersianKMPCalendar
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun KMPCalendar.toDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(timeMillis)
    return instant.toLocalDateTime(timeZone)
}

fun KMPCalendar.dayOfWeek(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Int {
    return toDate(timeZone).dayOfWeek.toDayNumber()
}

fun DayOfWeek.toDayNumber(): Int {
    return when (this) {
        DayOfWeek.SATURDAY -> 0
        DayOfWeek.SUNDAY -> 1
        DayOfWeek.MONDAY -> 2
        DayOfWeek.TUESDAY -> 3
        DayOfWeek.WEDNESDAY -> 4
        DayOfWeek.THURSDAY -> 5
        DayOfWeek.FRIDAY -> 6
        else -> error("Day Number is not available")
    }
}

internal val gregorianMonthNamesEn: Array<String> = arrayOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
)
internal val gregorianMonthNamesFa: Array<String> = arrayOf(
    "ژانویه",
    "فوریه",
    "مارس",
    "آوریل",
    "می",
    "ژوئن",
    "جولای",
    "آگوست",
    "سپتامبر",
    "اکتبر",
    "نوامبر",
    "دسامبر",
)

private val persianMonthNamesFa: Array<String> = arrayOf(
    "فروردین",
    "اردیبهشت",
    "خرداد",
    "تیر",
    "مرداد",
    "شهریور",
    "مهر",
    "آبان",
    "آذر",
    "دی",
    "بهمن",
    "اسفند"
)
private val persianMonthNamesEn: Array<String> = arrayOf(
    "Farvardin",
    "Ordibehesht",
    "Khordad",
    "Tir",
    "Mordad",
    "Shahrivar",
    "Mehr",
    "Aban",
    "Azar",
    "Day",
    "Bahman",
    "Esfand"
)

internal val dayNamesEn: Array<String> = arrayOf(
    "Saturday",
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
)
internal val dayNamesFa: Array<String> = arrayOf(
    "شنبه",
    "یکشنبه",
    "دوشنبه",
    "سه شنبه",
    "چهارشنبه",
    "پنجشنبه",
    "جمعه",
)

fun getMonthNames(
    calendarType: CalendarType,
    languageType: LanguageType
): Array<String> {
    return when (calendarType) {
        CalendarType.Gregorian -> {
            when (languageType) {
                LanguageType.English -> gregorianMonthNamesEn
                LanguageType.Persian -> gregorianMonthNamesFa
            }
        }

        CalendarType.Persian -> {
            when (languageType) {
                LanguageType.English -> persianMonthNamesEn
                LanguageType.Persian -> persianMonthNamesFa
            }
        }
    }
}

fun getIsLeapYear(year: Int, calendarType: CalendarType): Boolean {
    return when (calendarType) {
        CalendarType.Gregorian -> getGrgYearIsLeap(year)
        CalendarType.Persian -> getPersianYearIsLeap(year)
    }
}

internal fun getPersianYearIsLeap(year: Int): Boolean {
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

internal fun getGrgYearIsLeap(year: Int): Boolean {
    if (year % 4 == 0) {
        if (year % 100 == 0) {
            return year % 400 == 0
        }
        return true
    }
    return false
}

fun getDayNames(
    languageType: LanguageType
): Array<String> {
    return when (languageType) {
        LanguageType.English -> dayNamesEn
        LanguageType.Persian -> dayNamesFa
    }
}

fun getDayName(
    day: Int,
    languageType: LanguageType
): String {
    return when (languageType) {
        LanguageType.English -> dayNamesEn[day - 1]
        LanguageType.Persian -> dayNamesFa[day - 1]
    }
}

/**
 * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
 * Source & Free :: Version: 2.80 : [2020=1399]
 */
internal fun persianToGregorian(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    second: Int,
): GregorianKMPCalendar {
    var jy = year
    jy += 1595
    val out = intArrayOf(
        0,
        0,
        -355668 + (365 * jy) + ((jy / 33) * 8) + (((jy % 33) + 3) / 4) + day + (if ((month < 7)) (month - 1) * 31 else ((month - 7) * 30) + 186)
    )
    out[0] = 400 * (out[2] / 146097)
    out[2] %= 146097
    if (out[2] > 36524) {
        out[0] += 100 * (--out[2] / 36524)
        out[2] %= 36524
        if (out[2] >= 365) {
            out[2]++
        }
    }
    out[0] += 4 * (out[2] / 1461)
    out[2] %= 1461
    if (out[2] > 365) {
        out[0] += ((out[2] - 1) / 365)
        out[2] = (out[2] - 1) % 365
    }
    val salA = intArrayOf(
        0, 31, if (((out[0] % 4 == 0 && out[0] % 100 != 0) || (out[0] % 400 == 0))) 29 else 28,
        31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    )
    out[2]++
    while (out[1] < 13 && out[2] > salA[out[1]]) {
        out[2] -= salA[out[1]]
        out[1]++
    }
    return GregorianKMPCalendar(
        year = out[0],
        month = out[1],
        day = out[2],
        hour = hour,
        minute = minute,
        second = second,
    )
}

/**
 * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
 * Source & Free :: Version: 2.80 : [2020=1399]
 */
internal fun gregorianToPersian(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    second: Int,
): PersianKMPCalendar {
    val out: IntArray = intArrayOf(
        if ((month > 2)) (year + 1) else year,
        0,
        0
    )
    run {
        val gDM: IntArray =
            intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        out[2] =
            (355666 + (365 * year) + ((out[0] + 3) / 4) - ((out[0] + 99) / 100)
                ) + ((out[0] + 399) / 400) + day + gDM[month - 1]
    }
    out[0] = -1595 + (33 * (out[2] / 12053))
    out[2] %= 12053
    out[0] += 4 * (out[2] / 1461)
    out[2] %= 1461
    if (out[2] > 365) {
        out[0] += ((out[2] - 1) / 365)
        out[2] = (out[2] - 1) % 365
    }
    if (out[2] < 186) {
        out[1] = 1 + (out[2] / 31)
        out[2] = 1 + (out[2] % 31)
    } else {
        out[1] = 7 + ((out[2] - 186) / 30)
        out[2] = 1 + ((out[2] - 186) % 30)
    }
    return PersianKMPCalendar(
        year = out[0],
        month = out[1],
        day = out[2],
        hour = hour,
        minute = minute,
        second = second,
    )
}
