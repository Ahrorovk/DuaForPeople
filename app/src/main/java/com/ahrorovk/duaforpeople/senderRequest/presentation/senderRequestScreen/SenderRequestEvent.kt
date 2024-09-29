package com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen

sealed class SenderRequestEvent {

    data class OnDeeplinkChange(val id: String) : SenderRequestEvent()

    data class OnDuaFieldChange(val state: String) : SenderRequestEvent()

    data class OnNameChange(val state: String) : SenderRequestEvent()

    object AddDuaRequest : SenderRequestEvent()

    object MakeDua : SenderRequestEvent()

    object GetUserById : SenderRequestEvent()

    object GetDuaOfReceiverFromDeeplink : SenderRequestEvent()
}