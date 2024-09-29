package com.ahrorovk.duaforpeople.main.presentation.mainScreen

import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest

sealed class MainEvent {
    data class OnIsDoneChange(val id: Int, val request: DuaRequest) : MainEvent()

    object GetDuaRequestsByUid : MainEvent()

    object GoToRequest : MainEvent()

    object OnSaveChanges : MainEvent()
}