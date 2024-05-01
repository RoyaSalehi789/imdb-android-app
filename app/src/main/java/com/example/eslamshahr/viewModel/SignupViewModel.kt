package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eslamshahr.entities.User
import com.example.eslamshahr.model.Storage

import com.example.eslamshahr.repositories.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: RegisterRepository, application: Application)
    : AndroidViewModel(application), Observable{
    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<User>>()

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val inputPhone = MutableLiveData<String?>()

    @Bindable
    val inputUsername = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateTo = MutableLiveData<Boolean>()

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun back() {
        _navigateToLogin.value = true
    }

    fun signupButton() {
        if (inputName.value == null || inputPhone.value == null || inputEmail.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = true
                } else {
                    val name = inputName.value!!
                    val phone = inputPhone.value!!
                    val email = inputEmail.value!!
                    val password = inputPassword.value!!
                    val username = inputUsername.value!!
                    Storage.getInstance().username = username
                    insert(User(username, name, phone.toLong(), email, password ))
                    inputName.value = null
                    inputEmail.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    inputPhone.value = null
                    _navigateTo.value = true
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateTo.value = false
    }

    fun donetoast() {
        _errorToast.value = false
    }

    fun donetoastUserName() {
        _errorToast.value = false
    }

    private fun insert(user: User): Job = viewModelScope.launch {
        repository.insert(user)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}