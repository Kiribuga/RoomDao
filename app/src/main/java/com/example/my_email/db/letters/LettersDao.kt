package com.example.my_email.db.letters

import androidx.room.*
import com.example.my_email.db.users.Users

@Dao
interface LettersDao {

    @Query("SELECT * FROM ${LettersContract.TABLE_NAME}")
    suspend fun getAllLetters(): List<Letters>

    @Query("SELECT * FROM ${LettersContract.TABLE_NAME} WHERE ${LettersContract.Columns.TO_USER_EMAIL} = :to_user_email")
    suspend fun getLettersByToEmail(to_user_email: String): List<Letters>

    @Query("SELECT * FROM ${LettersContract.TABLE_NAME} WHERE ${LettersContract.Columns.FROM_USER_EMAIL} = :from_user_email")
    suspend fun getLettersByFromEmail(from_user_email: String): List<Letters>

    @Query("SELECT * FROM ${LettersContract.TABLE_NAME} WHERE ${LettersContract.Columns.ID} = :letterId")
    suspend fun getLettersById(letterId: Long): Letters?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLetter(letter: Letters)

    @Update
    suspend fun updateLetter(letter: Letters)

    @Delete
    suspend fun removeLetter(letter: Letters)

    @Query("DELETE FROM ${LettersContract.TABLE_NAME} WHERE ${LettersContract.Columns.ID} = :letterId")
    suspend fun removeLetterById(letterId: Long)
}