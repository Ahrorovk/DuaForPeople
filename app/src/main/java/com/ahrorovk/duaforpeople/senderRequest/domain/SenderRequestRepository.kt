package com.ahrorovk.duaforpeople.senderRequest.domain

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DeeplinkRequestState
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMResponse
import com.ahrorovk.duaforpeople.senderRequest.data.model.Notification
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService

interface SenderRequestRepository {
    val database: FirebaseFirestore

    val messaging: FirebaseMessagingService

    suspend fun sendMessage(
        notification: Notification
    ): FCMResponse

    suspend fun addDuaRequest(duaRequest: DuaRequest, onResult: (String) -> Unit)

    suspend fun makeDua(deeplinkRequest: DeeplinkRequest, onResult: (String) -> Unit)

    fun getDuaOfReceiverFromDeeplink(deeplink: String, onResult: (DeeplinkRequestState) -> Unit)
}