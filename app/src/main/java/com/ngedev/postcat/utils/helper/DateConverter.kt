package com.ngedev.postcat.utils.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun setLocalDateFormat(timestamp: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val date = sdf.parse(timestamp) as Date

        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}