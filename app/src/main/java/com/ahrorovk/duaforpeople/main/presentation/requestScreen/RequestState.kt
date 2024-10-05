package com.ahrorovk.duaforpeople.main.presentation.requestScreen

data class RequestState(
    val deeplink: String = "",
    val uid: String = "",
    val duaDescription: String = "",
    val name: String = "",
    val answer: String = "",
    val isLoading: Boolean = false,
    val fcmToken: String = ""
)
