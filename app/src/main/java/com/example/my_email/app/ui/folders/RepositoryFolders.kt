package com.example.my_email.app.ui.folders

import com.example.my_email.db.Database
import com.example.my_email.db.folders.Folders

class RepositoryFolders {

    private val foldersDao = Database.instance.foldersDao()

    suspend fun insertFolder(folders: List<Folders>) {
        foldersDao.insertFolder(folders)
    }

    suspend fun removeFolderById(idFolder: Long) {
        foldersDao.removeFolderById(idFolder)
    }

    suspend fun getFolderByEmailUser(user_email: String): List<Folders> {
        return foldersDao.getFolderByEmailUser(user_email)
    }

    suspend fun removeFoldersByEmailUser(user_email: String) {
        foldersDao.removeFoldersByEmailUser(user_email)
    }
}