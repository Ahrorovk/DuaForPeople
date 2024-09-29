package com.ahrorovk.duaforpeople.core.domain.models

data class DeeplinkRequest(
    val uidReceiver: String = "",
    val uidSender: String = "",
    val deeplink: String = "",
    val duaDescription: String = "",
    val name: String = "",
    val fcmTokenSender: String = "",
    val quantities: Int = 0
)
