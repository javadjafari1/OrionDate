package com.orion.date.language

object PersianGregorianLanguage : CalendarLanguage {
    override val weekDayNames: Array<String>
        get() = arrayOf(
            "ستردی",
            "ساندی",
            "ماندی",
            "تیوزدی",
            "وندزدی",
            "ترزدی",
            "فرایدی",
        )

    override val monthNames: Array<String>
        get() = arrayOf(
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
}