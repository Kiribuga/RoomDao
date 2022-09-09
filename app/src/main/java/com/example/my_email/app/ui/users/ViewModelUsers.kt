package com.example.my_email.app.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersWithRelation
import kotlinx.coroutines.launch

class ViewModelUsers : ViewModel() {

    private val repositoryUsers = RepositoryUsers()

    private val allUsersMutableLiveData = MutableLiveData<List<Users>>()
    private val getUserByIdMutableLiveData = MutableLiveData<Users?>()
    private val usersWithRelationMLD = MutableLiveData<List<UsersWithRelation>>()
    private val loadMLD = MutableLiveData<Boolean>()

    val allUsersLiveData: LiveData<List<Users>>
        get() = allUsersMutableLiveData

    val userByIdLiveData: LiveData<Users?>
        get() = getUserByIdMutableLiveData

    val usersWithRelationLD: LiveData<List<UsersWithRelation>>
        get() = usersWithRelationMLD

    val loadLD: LiveData<Boolean>
        get() = loadMLD

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                allUsersMutableLiveData.postValue(repositoryUsers.getAllUsers())
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error get all users", t)
                allUsersMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun getUsersWithRelation(user_email: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                usersWithRelationMLD.postValue(repositoryUsers.getUserWithRelations(user_email))
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error get users with relation", t)
                usersWithRelationMLD.postValue(emptyList())
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun saveUser(user: Users) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryUsers.saveUser(user)
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error save user", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun getUserById(userId: Long) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                getUserByIdMutableLiveData.postValue(repositoryUsers.getUserById(userId))
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error get user", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun removeUserByEmail(userEmail: String) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryUsers.removeUserByEmail(userEmail)
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error remove user", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }

    fun updateUser(user: Users) {
        loadMLD.postValue(true)
        viewModelScope.launch {
            try {
                repositoryUsers.updateUser(user)
            } catch (t: Throwable) {
                Log.d("ViewModelUsers", "error update user", t)
            } finally {
                loadMLD.postValue(false)
            }
        }
    }
}