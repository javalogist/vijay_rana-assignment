package com.javalogist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User
import com.javalogist.domain.usecase.AuthUseCase
import com.javalogist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    fun registerUser(user: User): LiveData<ResultWrapper<Unit>> {
        val progress = SingleLiveEvent<ResultWrapper<Unit>>()
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(ResultWrapper.Loading)
            val response = authUseCase.registerUser(user)
            progress.postValue(response)
        }
        return progress
    }

    fun loginUser(email: String, password: String): LiveData<ResultWrapper<Unit>> {
        val progress = SingleLiveEvent<ResultWrapper<Unit>>()
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(ResultWrapper.Loading)
            val response = authUseCase.loginUser(email, password)
            progress.postValue(response)
        }
        return progress
    }
}