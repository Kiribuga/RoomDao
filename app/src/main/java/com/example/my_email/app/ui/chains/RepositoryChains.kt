package com.example.my_email.app.ui.chains

import com.example.my_email.db.Database
import com.example.my_email.db.lettersChains.LettersChains

class RepositoryChains {

    private val chainsDao = Database.instance.lettersChainsDao()

    suspend fun getAllLettersChains(): List<LettersChains> {
        return chainsDao.getAllLettersChains()
    }

    suspend fun insertLettersChain(letterChain: LettersChains) {
        chainsDao.insertLettersChain(letterChain)
    }
}