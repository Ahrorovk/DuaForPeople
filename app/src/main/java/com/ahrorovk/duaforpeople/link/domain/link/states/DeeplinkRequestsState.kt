package com.ahrorovk.duaforpeople.link.domain.link.states

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest

data class DeeplinkRequestsState(
    val error: String = "",
    val response: List<DeeplinkRequest>? = null,
    val isLoading: Boolean = false
)
