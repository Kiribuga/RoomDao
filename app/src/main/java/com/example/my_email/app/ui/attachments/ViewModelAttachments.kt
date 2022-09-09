package com.example.my_email.app.ui.attachments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.attachments.Attachments
import kotlinx.coroutines.launch

class ViewModelAttachments : ViewModel() {

    private val repositoryAttachments = RepositoryAttachments()

    private val getAttachmentMLD = MutableLiveData<List<Attachments>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val getAttachmentLD: LiveData<List<Attachments>>
        get() = getAttachmentMLD

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun getAllAttachments() {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                getAttachmentMLD.postValue(repositoryAttachments.getAllAttachments())
            } catch (t: Throwable) {
                Log.d("ViewModelAttachments", "error get all attachments", t)
                getAttachmentMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun insertAttachment(attachments: Attachments) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryAttachments.insertAttachment(attachments)
            } catch (t: Throwable) {
                Log.d("ViewModelAttachments", "error insert attachment", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun deleteAttachment(idAttachment: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryAttachments.deleteAttachment(idAttachment)
            } catch (t: Throwable) {
                Log.d("ViewModelAttachments", "error delete attachment", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}