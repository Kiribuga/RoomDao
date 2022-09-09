package com.example.my_email.app.data

import com.example.my_email.db.folders.Folders
import com.example.my_email.db.users.Users

interface DataInterface {
    fun saveUser(user: Users)
    fun standardFolders(folders: List<Folders>)
    fun addFolder(folder: Folders)
    fun delUser(userEmail: String)
    fun updateUser(user: Users)
}