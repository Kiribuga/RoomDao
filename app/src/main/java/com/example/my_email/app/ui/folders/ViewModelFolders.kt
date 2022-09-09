package com.example.my_email.app.ui.folders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.folders.Folders
import kotlinx.coroutines.launch

class ViewModelFolders : ViewModel() {

    private val repositoryFolders = RepositoryFolders()

    private val foldersByEmailMLD = MutableLiveData<List<Folders>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val foldersByEmailLD: LiveData<List<Folders>>
        get() = foldersByEmailMLD

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun insertFolder(folders: List<Folders>) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryFolders.insertFolder(folders)
            } catch (t: Throwable) {
                Log.d("ViewModelFolders", "error save folders", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun removeFolderByTitle(idFolder: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryFolders.removeFolderById(idFolder)
            } catch (t: Throwable) {
                Log.d("ViewModelFolders", "error remove folder", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getFolderByEmailUser(user_email: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                foldersByEmailMLD.postValue(repositoryFolders.getFolderByEmailUser(user_email))
            } catch (t: Throwable) {
                Log.d("ViewModelFolders", "error get folders by email", t)
                foldersByEmailMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun removeFoldersByEmailUser(user_email: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryFolders.removeFoldersByEmailUser(user_email)
            } catch (t: Throwable) {
                Log.d("ViewModelFolders", "error remove folder by user email", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}