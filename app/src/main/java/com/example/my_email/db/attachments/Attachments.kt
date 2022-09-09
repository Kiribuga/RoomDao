package com.example.my_email.db.attachments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AttachmentsContract.TABLE_NAME)
data class Attachments(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AttachmentsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = AttachmentsContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = AttachmentsContract.Columns.TYPE)
    val type: String
)
