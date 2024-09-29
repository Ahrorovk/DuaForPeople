package com.ahrorovk.duaforpeople.auth.presentation.registrationScreen

import com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen.AuthorizationEvent

sealed class RegistrationEvent {
    data class OnEmailChange(val email: String) : RegistrationEvent()
    data class OnPasswordChange(val password: String) : RegistrationEvent()
    object Registration : RegistrationEvent()
    object GoToAuthorization : RegistrationEvent()
    object GoToMainScreen : RegistrationEvent()
}