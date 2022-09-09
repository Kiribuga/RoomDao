package com.example.my_email.db.users

import androidx.room.Embedded
import androidx.room.Relation
import com.example.my_email.db.folders.Folders
import com.example.my_email.db.folders.FoldersContract
import com.example.my_email.db.letters.Letters
import com.example.my_email.db.letters.LettersContract

data class UsersWithRelation(
    @Embedded
    val user: Users,
    @Relation(
        parentColumn = UsersContract.Columns.EMAIL,
        entityColumn = FoldersContract.Columns.USER_EMAIL
    )
    val folders: List<Folders>,
    @Relation(
        parentColumn = UsersContract.Columns.EMAIL,
        entityColumn = LettersContract.Columns.FROM_USER_EMAIL
    )
    val lettersFromUser: List<Letters>,
    @Relation(
        parentColumn = UsersContract.Columns.EMAIL,
        entityColumn = LettersContract.Columns.TO_USER_EMAIL
    )
    val lettersToUser: List<Letters>

)