package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: RegisterRepository,
application: Application) : AndroidViewModel(application), Observable{
    val user = repository.users

    @Bindable
    val inputUsername = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToRegister =MutableLiveData<Boolean>()
    val navigateToRegister: LiveData<Boolean> get() = _navigateToRegister

    private val _navigateToDetail =MutableLiveData<Boolean>()
    val navigateToDetail: LiveData<Boolean> get() = _navigateToDetail

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword


    fun signUP() {
        _navigateToRegister.value = true
    }

    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    if(usersNames.password == inputPassword.value){
                        Storage.getInstance().username = inputUsername.value!!.toString()
                        inputUsername.value = null
                        inputPassword.value = null
                        _navigateToDetail.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }

    fun doneNavigatingRegister() {
        _navigateToRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigateToDetail.value = false
    }


    fun donetoast() {
        _errorToast.value = false
    }


    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}