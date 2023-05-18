package com.javalogist.domain.repository

import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User

interface AuthRepository {
   suspend  fun registerUser(user: User): ResultWrapper<Unit>
   suspend fun loginUser(email: String, password: String): ResultWrapper<Unit>
}