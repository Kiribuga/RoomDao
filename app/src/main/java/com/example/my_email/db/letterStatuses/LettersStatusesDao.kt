package com.example.my_email.db.letterStatuses

import androidx.room.*
import com.example.my_email.db.lettersChains.LettersChainsContract

@Dao
interface LettersStatusesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createStatus(letterStatus: List<LetterStatuses>)

    @Query("SELECT * FROM ${LetterStatusesContract.TABLE_NAME}")
    suspend fun getAllStatuses(): List<LetterStatuses>

    @Query("SELECT * FROM ${LetterStatusesContract.TABLE_NAME} WHERE ${LetterStatusesContract.Columns.ID} = :statusId")
    suspend fun getStatusById(statusId: Long): LetterStatuses

    @Update
    suspend fun updateStatus(status: LetterStatuses)
}