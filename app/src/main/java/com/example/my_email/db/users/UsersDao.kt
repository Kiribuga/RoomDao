package com.example.my_email.db.users

import androidx.room.*

@Dao
interface UsersDao {

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME}")
    suspend fun getAllUsers(): List<Users>

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.EMAIL} = :user_email")
    suspend fun getUserWithRelations(user_email: String): List<UsersWithRelation>

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.ID} = :userId")
    suspend fun getUserById(userId: Long): Users?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: Users)

    @Query("DELETE FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.EMAIL} = :userEmail")
    suspend fun removeUserByEmail(userEmail: String)

    @Update
    suspend fun updateUser(user: Users)
}