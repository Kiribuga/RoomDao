package com.example.my_email.db.signatures

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SignaturesContract.TABLE_NAME)
data class Signatures(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SignaturesContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = SignaturesContract.Columns.TEXT_SIGNATURES)
    val text_signatures: String? = null
)
