package com.orion.date.language

object EnglishJalaliLanguage : CalendarLanguage {
    override val weekDayNames: Array<String>
        get() = arrayOf(
            "Shanbe",
            "Yek Shanbe",
            "Do Shanbe",
            "Se Shanbe",
            "Chahar Shanbe",
            "Panj Shanbe",
            "Jomee",
        )

    override val monthNames: Array<String>
        get() = arrayOf(
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
}
