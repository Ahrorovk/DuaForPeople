package com.ahrorovk.duaforpeople.link.presentation.linkScreen

import com.ahrorovk.duaforpeople.link.domain.link.states.DeeplinkRequestsState

data class LinkState(
    val uid: String = "",
    val deeplinkRequestState: DeeplinkRequestsState = DeeplinkRequestsState()
)
