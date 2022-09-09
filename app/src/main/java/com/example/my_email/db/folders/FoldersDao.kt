package com.example.my_email.db.folders

import androidx.room.*
import com.example.my_email.db.users.Users

@Dao
interface FoldersDao {

    @Query("SELECT * FROM ${FoldersContract.TABLE_NAME}")
    suspend fun getAllFolders(): List<Folders>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: List<Folders>)

    @Query("DELETE FROM ${FoldersContract.TABLE_NAME} WHERE ${FoldersContract.Columns.ID} = :idFolder")
    suspend fun removeFolderById(idFolder: Long)

    @Query("DELETE FROM ${FoldersContract.TABLE_NAME} WHERE ${FoldersContract.Columns.USER_EMAIL} = :user_email")
    suspend fun removeFoldersByEmailUser(user_email: String)

    @Query("SELECT * FROM ${FoldersContract.TABLE_NAME} WHERE ${FoldersContract.Columns.USER_EMAIL} = :user_email")
    suspend fun getFolderByEmailUser(user_email: String): List<Folders>
}