package com.example.my_email.app.ui.signatures

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.signatures.Signatures
import kotlinx.coroutines.launch

class ViewModelSignatures : ViewModel() {

    private val repositorySignatures = RepositorySignatures()

    private val allSignaturesMLD = MutableLiveData<List<Signatures>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val allSignaturesLD: LiveData<List<Signatures>>
        get() = allSignaturesMLD

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun getAllSignatures() {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                allSignaturesMLD.postValue(repositorySignatures.getAllSignatures())
            } catch (t: Throwable) {
                Log.d("ViewModelSignatures", "error get all signatures", t)
                allSignaturesMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun insertSignature(signature: Signatures) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositorySignatures.insertSignature(signature)
            } catch (t: Throwable) {
                Log.d("ViewModelSignatures", "error insert signature", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}