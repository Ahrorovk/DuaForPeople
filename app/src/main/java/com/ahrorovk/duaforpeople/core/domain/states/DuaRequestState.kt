package com.ahrorovk.duaforpeople.core.domain.states

import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest

data class DuaRequestState(
    val error: String = "",
    val response: List<DuaRequest>? = null,
    val isLoading: Boolean = false
)
