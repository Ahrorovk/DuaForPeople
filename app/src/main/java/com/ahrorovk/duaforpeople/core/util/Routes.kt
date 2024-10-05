package com.ahrorovk.duaforpeople.core.util

sealed class Routes(val route: String) {
    object RegistrationScreen : Routes("RegistrationScreen")

    object AuthorizationScreen : Routes("AuthorizationScreen")

    object LinkScreen : Routes("LinkScreen")

    object MainScreen : Routes("MainScreen")

    object UserScreen : Routes("UserScreen")

    object StartScreen : Routes("StartScreen")

    object RequestScreen : Routes("RequestScreen")

    object SenderRequestScreen : Routes("SenderRequestScreen")
}