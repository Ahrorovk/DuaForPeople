package com.ahrorovk.duaforpeople.auth.domain

import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): AuthResult

    suspend fun registerUser(email: String, password: String): AuthResult

    suspend fun verifyEmail(email: String): Void?
}