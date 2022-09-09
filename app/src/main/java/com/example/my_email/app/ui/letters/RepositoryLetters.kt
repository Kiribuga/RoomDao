package com.example.my_email.app.ui.letters

import com.example.my_email.db.Database
import com.example.my_email.db.letters.Letters

class RepositoryLetters {

    private val lettersDao = Database.instance.lettersDao()

    suspend fun getAllLetters(): List<Letters> {
        return lettersDao.getAllLetters()
    }

    suspend fun getLettersByToEmail(to_user_email: String): List<Letters> {
        return lettersDao.getLettersByToEmail(to_user_email)
    }

    suspend fun getLettersByFromEmail(from_user_email: String): List<Letters> {
        return lettersDao.getLettersByFromEmail(from_user_email)
    }

    suspend fun getLettersById(letterId: Long): Letters? {
        return lettersDao.getLettersById(letterId)
    }

    suspend fun createLetter(letter: Letters) {
        lettersDao.createLetter(letter)
    }

    suspend fun removeLetter(letter: Letters) {
        lettersDao.removeLetter(letter)
    }

    suspend fun removeLetterById(letterId: Long) {
        lettersDao.removeLetterById(letterId)
    }

    suspend fun updateLetter(letter: Letters) {
        lettersDao.updateLetter(letter)
    }
}