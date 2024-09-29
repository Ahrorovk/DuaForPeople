package com.ahrorovk.duaforpeople.main.domain

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DuaRequestState
import com.google.firebase.firestore.FirebaseFirestore

interface MainRepository {
    val database: FirebaseFirestore

    fun getDuaRequestsByUid(uid: String, onResult: (DuaRequestState) -> Unit)

    fun updateStateOfRequest(duaRequest: DuaRequest, onResult: (String) -> Unit)

    fun addDeeplinkRequest(deeplinkRequest: DeeplinkRequest, onResult: (String) -> Unit)
}