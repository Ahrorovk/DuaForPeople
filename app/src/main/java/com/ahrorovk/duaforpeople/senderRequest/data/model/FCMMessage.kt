package com.ahrorovk.duaforpeople.senderRequest.data.model

data class Notification(
    val body: String,
    val title: String,
    val token: String
)

data class FCMResponse(
    val statusCode: Int,
    val status: String
)
