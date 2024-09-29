package com.ahrorovk.duaforpeople.main.presentation.requestScreen

sealed class RequestEvent {
    data class OnDuaDescriptionChange(val state: String) : RequestEvent()

    data class OnNameChange(val state: String) : RequestEvent()

    data class OnDeeplinkChange(val state: String) : RequestEvent()
}