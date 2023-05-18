package com.javalogist.data.repository.auth

import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User
import com.javalogist.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource) : AuthRepository {
    override suspend fun registerUser(user: User): ResultWrapper<Unit> {
        return authRemoteDataSource.registerUser(user)
    }

    override suspend fun loginUser(email: String, password: String): ResultWrapper<Unit> {
        return authRemoteDataSource.loginUser(email, password)
    }

}