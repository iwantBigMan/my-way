package com.hyeon.side.sideapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyeon.side.sideapp.domain.usecase.auth.AuthUseCase
import com.hyeon.side.sideapp.data.repository.AuthRepositoryImpl
import com.hyeon.side.sideapp.data.repository.FriendsRepositoryImpl
import com.hyeon.side.sideapp.data.repository.UserRepositoryImpl
import com.hyeon.side.sideapp.domain.repository.AuthRepository
import com.hyeon.side.sideapp.domain.repository.FriendsRepository
import com.hyeon.side.sideapp.domain.repository.UserRepository
import com.hyeon.side.sideapp.domain.usecase.friend.GetFriendsListUseCase
import com.hyeon.side.sideapp.domain.usecase.user.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFriendsRepository(
        friendsRepositoryImpl: FriendsRepositoryImpl
    ): FriendsRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    companion object {
        @Provides
        @Singleton
        fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase {
            return AuthUseCase(authRepository)
        }

        @Provides
        @Singleton
        fun provideFriendsUseCase(friendsRepository: FriendsRepository): GetFriendsListUseCase {
            return GetFriendsListUseCase(friendsRepository)
        }

        @Provides
        @Singleton
        fun provideUserRepository(userRepository: UserRepository): UserUseCase {
            return UserUseCase(userRepository)
        }

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}