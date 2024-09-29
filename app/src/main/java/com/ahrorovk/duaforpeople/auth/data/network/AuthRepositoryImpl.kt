package com.ahrorovk.duaforpeople.auth.data.network

import com.ahrorovk.duaforpeople.auth.domain.AuthRepository
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String): AuthResult = firebaseAuth
        .signInWithEmailAndPassword(email, password)
        .await()

    override suspend fun registerUser(email: String, password: String): AuthResult = firebaseAuth
        .createUserWithEmailAndPassword(email, password)
        .await()

    val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setUrl("https://duaforpeople-c45de.firebaseapp.com/__/auth/action")
        .setHandleCodeInApp(true)
        .setAndroidPackageName(
            "com.ahrorovk.duaforpeople",
            true,
            "12"
        )
        .build()

    override suspend fun verifyEmail(email: String): Void? = firebaseAuth
        .sendSignInLinkToEmail(email, actionCodeSettings)
        .await()
}