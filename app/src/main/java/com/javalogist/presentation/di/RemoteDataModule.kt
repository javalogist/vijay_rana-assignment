package com.javalogist.presentation.di


import com.google.firebase.database.FirebaseDatabase
import com.javalogist.data.repository.auth.AuthRemoteDataSource
import com.javalogist.data.repository.auth.AuthRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun providesAuthRemoteDataSource(firebaseDatabase: FirebaseDatabase): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(firebaseDatabase)
    }
}