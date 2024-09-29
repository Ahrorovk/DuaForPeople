package com.ahrorovk.duaforpeople.user.presentation

import com.ahrorovk.duaforpeople.user.domain.states.UserModelState

data class UserState(
    val uid: String = "",
    val limit: Int = 0,
    val userModelState: UserModelState = UserModelState()
)
