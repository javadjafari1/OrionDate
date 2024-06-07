package com.orion.date.language

object EnglishGregorianLanguage : CalendarLanguage {
    override val weekDayNames: Array<String>
        get() = arrayOf(
            "Saturday",
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
        )

    override val monthNames: Array<String>
        get() = arrayOf(
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
}
