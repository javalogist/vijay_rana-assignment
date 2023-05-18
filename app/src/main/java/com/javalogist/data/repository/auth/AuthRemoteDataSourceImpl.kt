package com.javalogist.data.repository.auth

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.javalogist.data.model.ResultWrapper
import com.javalogist.data.model.User
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(firebaseDatabase: FirebaseDatabase) :
    AuthRemoteDataSource {
    private val authReference: DatabaseReference = firebaseDatabase.getReference("auth")

    override suspend fun registerUser(user: User): ResultWrapper<Unit> {
        return try {
            val userRef = authReference.push()
            user.userId = userRef.key.toString()
            userRef.setValue(user).await()
            ResultWrapper.Success(value = Unit)
        } catch (e: Exception) {
            ResultWrapper.Error(e.toString())
        }
    }

    override suspend fun loginUser(email: String, password: String): ResultWrapper<Unit> {
        try {
            val data =
                authReference.orderByChild("emailAddress").equalTo(email).limitToFirst(1).get()
                    .await()
            if (data.exists() && data != null && data.value != null) {
                 data.children.forEach {
                    val user =  it.getValue(User::class.java)
                     Log.d("MY-TAG", "user is  ${user?.password}")
                     return if (user?.password.equals(password)) {
                         ResultWrapper.Success(Unit);
                     } else {
                         ResultWrapper.Error("Password invalid")
                     }
                 }

            }

        } catch (e: Exception) {
            Log.d("MY-TAG", "error is  $e")
            return ResultWrapper.Error(e.toString())
        }
        return ResultWrapper.Error("Something went wrong")
    }
}