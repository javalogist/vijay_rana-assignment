package com.javalogist.domain.usecase

import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User
import com.javalogist.domain.repository.AuthRepository

class AuthUseCase(private val authRepository: AuthRepository) {

   suspend fun registerUser(user:User):ResultWrapper<Unit>{
      return  authRepository.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): ResultWrapper<Unit> {
        return authRepository.loginUser(email, password)
    }
}