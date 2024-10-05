package com.ahrorovk.duaforpeople.app.navigation

sealed class NavigationEvent {
    object ClearToken : NavigationEvent()
}