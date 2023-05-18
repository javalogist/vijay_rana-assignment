package com.javalogist.data.repository.auth

import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User

interface AuthRemoteDataSource {

  suspend  fun registerUser(user: User): ResultWrapper<Unit>
 suspend fun loginUser(email: String, password: String): ResultWrapper<Unit>
}