package com.ahrorovk.duaforpeople.core.util

fun doesScreenHaveBottomBar(currentScreen: String) =
    currentScreen != Routes.RegistrationScreen.route &&
            currentScreen != Routes.AuthorizationScreen.route &&
            currentScreen != Routes.RequestScreen.route &&
            currentScreen != Routes.StartScreen.route

fun doesScreenHavePopBack(currentScreen: String): Boolean {
    return currentScreen != Routes.MainScreen.route &&
            currentScreen != Routes.SenderRequestScreen.route &&
            currentScreen != Routes.UserScreen.route &&
            currentScreen != Routes.StartScreen.route
}

fun doesScreenHaveTopBar(currentScreen: String): Boolean {
    return currentScreen != Routes.RegistrationScreen.route &&
            currentScreen != Routes.AuthorizationScreen.route &&
            currentScreen != Routes.StartScreen.route
}


fun getTopBarTitle(currentScreen: String): String {
    return when (currentScreen) {
        Routes.MainScreen.route -> "Main"
        Routes.RequestScreen.route -> "Link"
        Routes.SenderRequestScreen.route -> "Dua"
        Routes.UserScreen.route -> "Profile"
        else -> ""
    }
}
