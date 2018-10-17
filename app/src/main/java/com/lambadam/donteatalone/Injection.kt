package com.lambadam.donteatalone

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.auth.AuthManagerImp
import com.lambadam.data.mapper.UserEntityMapper
import com.lambadam.data.user.UserRepositoryImp
import com.lambadam.domain.auth.Login
import com.lambadam.domain.user.GetUser
import com.lambadam.donteatalone.executor.CoroutineDispatcherProviderImp

object Injection {
    val dispatcherProvider = CoroutineDispatcherProviderImp()
    private val userEntityMapper = UserEntityMapper()

    fun provideLogin(context: Context) = Login(provideAuthManager(context), dispatcherProvider)

    fun provideGetUser(context: Context)  = GetUser(provideUserRepository(context), dispatcherProvider)

    private fun provideAuthManager(context: Context): AuthManagerImp{
        FirebaseApp.initializeApp(context)
        return AuthManagerImp.getInstance(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }

    private fun provideUserRepository(context: Context): UserRepositoryImp {
        FirebaseApp.initializeApp(context)
        return UserRepositoryImp.getInstance(FirebaseFirestore.getInstance(), userEntityMapper)
    }
}
