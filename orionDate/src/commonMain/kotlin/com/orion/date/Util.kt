package com.orion.date

import com.orion.date.calendars.GregorianKMPCalendar
import com.orion.date.calendars.JalaliKMPCalendar

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
): JalaliKMPCalendar {
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
    return JalaliKMPCalendar(
        year = out[0],
        month = out[1],
        day = out[2],
        hour = hour,
        minute = minute,
        second = second,
    )
}
