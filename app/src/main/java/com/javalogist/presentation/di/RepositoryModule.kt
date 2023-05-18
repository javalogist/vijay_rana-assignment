package com.javalogist.presentation.di


import com.javalogist.data.repository.auth.AuthRemoteDataSource
import com.javalogist.data.repository.auth.AuthRepositoryImpl
import com.javalogist.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }


}