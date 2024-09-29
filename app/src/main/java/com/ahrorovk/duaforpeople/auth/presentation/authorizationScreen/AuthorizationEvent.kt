package com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen

sealed class AuthorizationEvent {
    data class OnEmailChange(val email: String) : AuthorizationEvent()
    data class OnPasswordChange(val password: String) : AuthorizationEvent()
    object Authorization : AuthorizationEvent()
    object GoToRegistration : AuthorizationEvent()
    object GoToMainScreen : AuthorizationEvent()
}