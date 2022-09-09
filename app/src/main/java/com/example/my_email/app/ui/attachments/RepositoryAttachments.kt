package com.example.my_email.app.ui.attachments

import com.example.my_email.db.Database
import com.example.my_email.db.attachments.Attachments

class RepositoryAttachments {

    private val attachmentsDao = Database.instance.attachmentsDao()

    suspend fun getAllAttachments(): List<Attachments> {
        return attachmentsDao.getAllAttachments()
    }

    suspend fun insertAttachment(attachment: Attachments) {
        attachmentsDao.insertAttachment(attachment)
    }

    suspend fun deleteAttachment(idAttachment: Long) {
        attachmentsDao.deleteAttachment(idAttachment)
    }
}