package com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.domain.states.DeeplinkRequestState

data class SenderRequestState(
    val deeplinkRequest: DeeplinkRequest = DeeplinkRequest(),
    val uid: String = "",
    val dua: String = "",
    val name: String = "",
    val uidReceiver: String = "",
    val deeplink: String = "",
    val deeplinkRequestState: DeeplinkRequestState = DeeplinkRequestState(),
    val limit: Int = 0,
    val isLoading: Boolean = false,
    val response: String = "",
    val quantities: Int = 0,
    val fcmToken: String = ""
)
