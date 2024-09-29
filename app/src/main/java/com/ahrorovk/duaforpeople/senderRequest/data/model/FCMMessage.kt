package com.ahrorovk.duaforpeople.senderRequest.data.model

data class Notification(
    val body: String,
    val title: String
)

data class Message(
    val topic: String,
    val notification: Notification
)

data class FCMMessage(
    val message: Message
)

data class FCMResponse(
    val name: String
)
