package com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen

import com.ahrorovk.duaforpeople.auth.domain.auth.states.AuthResponseState

data class AuthorizationState(
    val email: String = "",
    val password: String = "",
    val authResponseState: AuthResponseState = AuthResponseState()
)
