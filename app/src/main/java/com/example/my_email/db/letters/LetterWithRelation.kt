package com.example.my_email.db.letters

import androidx.room.Embedded
import androidx.room.Relation
import com.example.my_email.db.attachments.Attachments
import com.example.my_email.db.attachments.AttachmentsContract
import com.example.my_email.db.folders.Folders
import com.example.my_email.db.folders.FoldersContract
import com.example.my_email.db.letterStatuses.LetterStatuses
import com.example.my_email.db.letterStatuses.LetterStatusesContract
import com.example.my_email.db.lettersChains.LettersChains
import com.example.my_email.db.lettersChains.LettersChainsContract
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersContract

data class LetterWithRelation(
    @Embedded
    val letter: Letters,
    @Relation(
        parentColumn = UsersContract.Columns.EMAIL,
        entityColumn = LettersContract.Columns.TO_USER_EMAIL
    )
    val idUser: Users,

    @Relation(
        parentColumn = AttachmentsContract.Columns.ID,
        entityColumn = LettersContract.Columns.ATTACHMENTS_TITLE
    )
    val attachments: List<Attachments>,
    @Relation(
        parentColumn = LettersChainsContract.Columns.ID,
        entityColumn = LettersContract.Columns.CHAIN_TITLE
    )
    val chain: LettersChains,
    @Relation(
        parentColumn = LetterStatusesContract.Columns.TEXT_STATUSES,
        entityColumn = LettersContract.Columns.STATUS_TEXT
    )
    val status: LetterStatuses
)
