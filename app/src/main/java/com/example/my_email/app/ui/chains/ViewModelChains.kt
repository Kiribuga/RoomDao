package com.example.my_email.app.ui.chains

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.lettersChains.LettersChains
import kotlinx.coroutines.launch

class ViewModelChains : ViewModel() {

    private val repositoryChains = RepositoryChains()

    private val allChainsMLD = MutableLiveData<List<LettersChains>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun getAllLettersChains() {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                allChainsMLD.postValue(repositoryChains.getAllLettersChains())
            } catch (t: Throwable) {
                Log.d("ViewModelChains", "error get all chains", t)
                allChainsMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun insertLettersChain(letterChain: LettersChains) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryChains.insertLettersChain(letterChain)
            } catch (t: Throwable) {
                Log.d("ViewModelChains", "error insert chain", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}