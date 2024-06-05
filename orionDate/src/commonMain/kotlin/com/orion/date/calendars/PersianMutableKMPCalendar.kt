package com.orion.date.calendars

import com.orion.date.toDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant

class PersianMutableKMPCalendar(
    override val timeMillis: Long,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
) : MutableKMPCalendar, KMPCalendar by PersianKMPCalendar(
    milliseconds = timeMillis,
    timezone = timeZone,
) {
    private var _plus: Long = 0
    private var _minus: Long = 0

    override fun plus(value: Long, unit: DateTimeUnit): PersianMutableKMPCalendar {
        val newMillis = toDate()
            .toInstant(TimeZone.currentSystemDefault())
            .plus(
                value = value,
                unit = unit,
                timeZone = TimeZone.currentSystemDefault()
            )
            .toEpochMilliseconds()

        _plus += (newMillis - timeMillis)
        return this
    }


    override fun minus(value: Long, unit: DateTimeUnit): PersianMutableKMPCalendar {
        val newMillis = toDate()
            .toInstant(TimeZone.currentSystemDefault())
            .minus(
                value = value,
                unit = unit,
                timeZone = TimeZone.currentSystemDefault()
            )
            .toEpochMilliseconds()

        _minus += (timeMillis - newMillis)
        return this
    }


    override fun convert(): PersianMutableKMPCalendar {
        return PersianMutableKMPCalendar(timeMillis + _plus - _minus)
    }
}
