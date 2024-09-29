package com.ahrorovk.duaforpeople.user.domain.states

import com.ahrorovk.duaforpeople.user.domain.models.UserModel

data class UserModelState(
    val error: String = "",
    val response: UserModel? = null,
    val isLoading: Boolean = false
)
