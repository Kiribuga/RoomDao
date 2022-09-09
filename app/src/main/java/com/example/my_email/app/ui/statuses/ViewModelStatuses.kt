package com.example.my_email.app.ui.statuses

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.letterStatuses.LetterStatuses
import kotlinx.coroutines.launch

class ViewModelStatuses : ViewModel() {

    private val repositoryStatuses = RepositoryStatuses()

    private val statusByIdMLD = MutableLiveData<LetterStatuses>()
    private val loadMLD = MutableLiveData<Boolean>()

    val statusByIdLD: LiveData<LetterStatuses?>
        get() = statusByIdMLD

    fun createStatus() {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryStatuses.createStatus()
            } catch (t: Throwable) {
                Log.d("ViewModelStatuses", "error created status", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getStatusById(statusId: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                statusByIdMLD.postValue(repositoryStatuses.getStatusById(statusId))
            } catch (t: Throwable) {
                Log.d("ViewModelStatuses", "error get status by id", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun updateStatus(status: LetterStatuses) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryStatuses.updateStatus(status)
            } catch (t: Throwable) {
                Log.d("ViewModelStatuses", "error update status", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}