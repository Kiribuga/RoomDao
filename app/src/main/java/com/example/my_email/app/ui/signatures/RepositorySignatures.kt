package com.example.my_email.app.ui.signatures

import com.example.my_email.db.Database
import com.example.my_email.db.signatures.Signatures

class RepositorySignatures {

    private val signaturesDao = Database.instance.signaturesDao()

    suspend fun getAllSignatures(): List<Signatures> {
        return signaturesDao.getAllSignatures()
    }

    suspend fun insertSignature(signature: Signatures) {
        signaturesDao.insertSignature(signature)
    }
}