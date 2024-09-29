package com.ahrorovk.duaforpeople.start.presentation

sealed class StartEvent {
    object GoToMain : StartEvent()

    object GoToAuthorization : StartEvent()
}