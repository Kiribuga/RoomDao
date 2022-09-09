package com.example.my_email.db.letterStatuses

import androidx.room.TypeConverter

class LetterStatusConverter {

    @TypeConverter
    fun convertToString(status: Status): String = status.name

    @TypeConverter
    fun convertToStatus(statusString: String): Status = Status.valueOf(statusString)
}