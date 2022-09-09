package com.example.my_email.db.letters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter

class ConverterInstant {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
        .withZone(ZoneId.systemDefault())

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun convertToString(instant: Instant): String = formatter.format(instant)

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun convertToInstant(instantString: String): Instant =
        ZonedDateTime.parse(instantString, formatter).toInstant()
}