package com.example.my_email.db.signatures

import androidx.room.*
import com.example.my_email.db.users.Users

@Dao
interface SignaturesDao {

    @Query("SELECT * FROM ${SignaturesContract.TABLE_NAME}")
    suspend fun getAllSignatures(): List<Signatures>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSignature(signature: Signatures)

    @Update
    suspend fun updateSignature(signature: Signatures)
}