package com.ahrorovk.duaforpeople.user.presentation

sealed class UserEvent {
    object AddLimits : UserEvent()

    object GetUserById : UserEvent()

    data class OnLimitChange(val limit: Int) : UserEvent()
}
