package com.example.my_email.db.folders

import androidx.room.*
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersContract

@Entity(
    tableName = FoldersContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = [UsersContract.Columns.EMAIL],
            childColumns = [FoldersContract.Columns.USER_EMAIL]
        )
    ]
)
data class Folders(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FoldersContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = FoldersContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = FoldersContract.Columns.USER_EMAIL)
    val user_email: String
)
