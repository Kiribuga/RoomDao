package com.example.my_email.app.ui.statuses

import com.example.my_email.db.Database
import com.example.my_email.db.letterStatuses.LetterStatuses
import com.example.my_email.db.letterStatuses.Status

class RepositoryStatuses {

    private val statusesDao = Database.instance.lettersStatusesDao()

    suspend fun createStatus() {
        statusesDao.createStatus(
            listOf(
                LetterStatuses(
                    id = 1,
                    text_statuses = Status.NO_READ
                ),
                LetterStatuses(
                    id = 2,
                    text_statuses = Status.READ
                )
            )
        )
    }

    suspend fun getStatusById(statusId: Long): LetterStatuses {
        return statusesDao.getStatusById(statusId)
    }

    suspend fun updateStatus(status: LetterStatuses) {
        statusesDao.updateStatus(status)
    }
}