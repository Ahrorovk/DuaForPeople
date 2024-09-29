package com.ahrorovk.duaforpeople.auth.domain.auth.states

import com.google.firebase.auth.AuthResult

data class AuthResponseState(
    val isLoading: Boolean = false,
    val responseState: AuthResult? = null,
    val error: String = ""
)
