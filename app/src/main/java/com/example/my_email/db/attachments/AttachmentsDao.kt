package com.example.my_email.db.attachments

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.my_email.db.users.Users

@Dao
interface AttachmentsDao {

    @Query("SELECT * FROM ${AttachmentsContract.TABLE_NAME}")
    suspend fun getAllAttachments(): List<Attachments>

    @Query("SELECT * FROM ${AttachmentsContract.TABLE_NAME} WHERE ${AttachmentsContract.Columns.ID} = :idAttachment")
    suspend fun getAttachmentById(idAttachment: Long): Attachments?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttachment(attachments: Attachments)

    @Query("DELETE FROM ${AttachmentsContract.TABLE_NAME} WHERE ${AttachmentsContract.Columns.ID} = :idAttachment")
    suspend fun deleteAttachment(idAttachment: Long)
}