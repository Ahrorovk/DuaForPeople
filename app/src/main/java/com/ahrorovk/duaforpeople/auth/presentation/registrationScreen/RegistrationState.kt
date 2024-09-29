package com.ahrorovk.duaforpeople.auth.presentation.registrationScreen

import com.ahrorovk.duaforpeople.auth.domain.registr.states.RegistrResponseState

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val registrationState: RegistrResponseState = RegistrResponseState()
)
