package com.example.my_email.db.letters

import androidx.room.TypeConverter

class ConvertTypeLetter {

    @TypeConverter
    fun convertToString(typeLetter: TypeLetter): String = typeLetter.name

    @TypeConverter
    fun convertToTypeLetter(typeLetterString: String): TypeLetter =
        TypeLetter.valueOf(typeLetterString)
}