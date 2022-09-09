package com.example.my_email.db.letterStatuses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = LetterStatusesContract.TABLE_NAME)
data class LetterStatuses(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = LetterStatusesContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = LetterStatusesContract.Columns.TEXT_STATUSES)
    val text_statuses: Status
)
