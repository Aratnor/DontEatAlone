package com.lambadam.donteatalone

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lambadam.data.auth.AuthManagerImp
import com.lambadam.domain.auth.Login
import com.lambadam.donteatalone.executor.CoroutineDispatcherProviderImp

object Injection {
    val dispatcherProvider = CoroutineDispatcherProviderImp()

    fun provideLogin(context: Context) = Login(provideAuthManager(context), dispatcherProvider)

    private fun provideAuthManager(context: Context): AuthManagerImp{
        FirebaseApp.initializeApp(context)
        return AuthManagerImp(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }
}
