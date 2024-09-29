package com.ahrorovk.duaforpeople.auth.domain.registr.states

import com.google.firebase.auth.AuthResult

data class RegistrResponseState(
    val isLoading: Boolean = false,
    val responseState: AuthResult? = null,
    val error: String = ""
)
