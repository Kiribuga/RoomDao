package com.example.my_email.db.users

import androidx.room.*

@Entity(tableName = UsersContract.TABLE_NAME,
    indices = [Index(UsersContract.Columns.EMAIL, unique = true)]
    )
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UsersContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = UsersContract.Columns.FIRST_NAME)
    val first_name: String,
    @ColumnInfo(name = UsersContract.Columns.LAST_NAME)
    val last_name: String,
    @ColumnInfo(name = UsersContract.Columns.EMAIL)
    val email: String,
    @ColumnInfo(name = UsersContract.Columns.AVATAR)
    val avatar: String,
    @ColumnInfo(name = UsersContract.Columns.AGE)
    val age: Int = 0
)