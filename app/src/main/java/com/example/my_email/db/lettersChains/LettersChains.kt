package com.example.my_email.db.lettersChains

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LettersChainsContract.TABLE_NAME)
data class LettersChains(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LettersChainsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = LettersChainsContract.Columns.TITLE)
    val title: String
)
