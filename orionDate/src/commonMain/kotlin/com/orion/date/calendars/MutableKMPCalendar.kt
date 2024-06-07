package com.orion.date.calendars

import kotlinx.datetime.DateTimeUnit

interface MutableKMPCalendar {

    fun plus(value: Long, unit: DateTimeUnit): MutableKMPCalendar
    fun minus(value: Long, unit: DateTimeUnit): MutableKMPCalendar
    fun convert(): MutableKMPCalendar
}
