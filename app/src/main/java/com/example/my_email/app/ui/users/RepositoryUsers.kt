package com.example.my_email.app.ui.users

import com.example.my_email.db.Database
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersWithRelation

class RepositoryUsers {

    private val userDao = Database.instance.usersDao()

    suspend fun getAllUsers(): List<Users> {
        return userDao.getAllUsers()
    }

    suspend fun getUserWithRelations(user_email: String): List<UsersWithRelation> {
        return userDao.getUserWithRelations(user_email)
    }

    suspend fun getUserById(userId: Long): Users? {
        return userDao.getUserById(userId)
    }

    suspend fun saveUser(user: Users) {
        userDao.insertUsers(user)
    }

    suspend fun removeUserByEmail(userEmail: String) {
        userDao.removeUserByEmail(userEmail)
    }

    suspend fun updateUser(user: Users) {
        userDao.updateUser(user)
    }
}