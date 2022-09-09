package com.example.my_email.db.lettersChains

import androidx.room.*
import com.example.my_email.db.users.Users

@Dao
interface LettersChainsDao {

    @Query("SELECT * FROM ${LettersChainsContract.TABLE_NAME}")
    suspend fun getAllLettersChains(): List<LettersChains>

    @Query("SELECT * FROM ${LettersChainsContract.TABLE_NAME} WHERE ${LettersChainsContract.Columns.ID} = :chainId")
    suspend fun getChainById(chainId: Long): LettersChains?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLettersChain(letterChain: LettersChains)

    @Delete
    suspend fun removeChain(chains: LettersChains)

    @Query("DELETE FROM ${LettersChainsContract.TABLE_NAME} WHERE ${LettersChainsContract.Columns.ID} = :chainId")
    suspend fun removeChainById(chainId: Long)
}