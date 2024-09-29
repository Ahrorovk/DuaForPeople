package com.ahrorovk.duaforpeople.main.presentation.mainScreen

import com.ahrorovk.duaforpeople.core.domain.states.DuaRequestState

data class MainState(
    val uid: String = "",
    var stateOfRequests: DuaRequestState = DuaRequestState()
)
