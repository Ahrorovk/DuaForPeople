package com.ahrorovk.duaforpeople.core.domain.states

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest

data class DeeplinkRequestState(
    val error: String = "",
    val response: DeeplinkRequest? = null,
    val isLoading: Boolean = false
)
