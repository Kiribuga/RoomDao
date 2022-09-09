package com.example.my_email.app.ui.letters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.letters.Letters
import kotlinx.coroutines.launch

class ViewModelLetters : ViewModel() {

    private val repositoryLetters = RepositoryLetters()

    private val allLettersMLD = MutableLiveData<List<Letters>>()
    private val letterByIdMLD = MutableLiveData<Letters?>()
    private val letterByToEmailMLD = MutableLiveData<List<Letters>>()
    private val letterByFromEmailMLD = MutableLiveData<List<Letters>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val allLettersLD: LiveData<List<Letters>>
        get() = allLettersMLD

    val letterByIdLD: LiveData<Letters?>
        get() = letterByIdMLD

    val letterByToEmailLD: LiveData<List<Letters>>
        get() = letterByToEmailMLD

    val letterByFromEmailLD: LiveData<List<Letters>>
        get() = letterByFromEmailMLD

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun getAllLetters() {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                allLettersMLD.postValue(repositoryLetters.getAllLetters())
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error get all letters", t)
                allLettersMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getLettersByToEmail(to_user_email: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                letterByToEmailMLD.postValue(repositoryLetters.getLettersByToEmail(to_user_email))
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error get letter by to email", t)
                letterByToEmailMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getLettersByFromEmail(from_user_email: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                letterByFromEmailMLD.postValue(repositoryLetters.getLettersByFromEmail(from_user_email))
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error get letter by from email", t)
                letterByFromEmailMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getLettersById(letterId: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                letterByIdMLD.postValue(repositoryLetters.getLettersById(letterId))
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error get letter by id", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun createLetter(letter: Letters) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryLetters.createLetter(letter)
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error created letter", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun removeLetterById(letterId: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryLetters.removeLetterById(letterId)
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error remove letter by id", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun updateLetter(letter: Letters) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryLetters.updateLetter(letter)
            } catch (t: Throwable) {
                Log.d("ViewModelLetters", "error update letter", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}