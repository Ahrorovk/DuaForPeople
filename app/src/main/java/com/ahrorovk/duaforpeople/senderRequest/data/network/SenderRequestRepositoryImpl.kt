package com.ahrorovk.duaforpeople.senderRequest.data.network

import android.util.Log
import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DeeplinkRequestState
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMMessage
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMResponse
import com.ahrorovk.duaforpeople.senderRequest.data.network.remote.SenderRequestApi
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessagingService
import java.util.UUID
import javax.inject.Inject

class SenderRequestRepositoryImpl @Inject constructor(
    private val api: SenderRequestApi
) : SenderRequestRepository {
    override val database: FirebaseFirestore = Firebase.firestore

    override val messaging = FirebaseMessagingService()

    override suspend fun sendMessage(authorization: String, fcmMessage: FCMMessage): FCMResponse {
        return api.sendMessage(authorization, fcmMessage)
    }

    override suspend fun addDuaRequest(duaRequest: DuaRequest, onResult: (String) -> Unit) {
        if (duaRequest.uidReceiver.isNotEmpty()) {
            val id = UUID.randomUUID().toString()
            database.collection("duaRequests")
                .document(id)
                .set(duaRequest.copy(id = id))
                .addOnSuccessListener {
                    Log.e("SUCCESS", "GET_SUCCESS_addDuaRequest->$it")

                    onResult("Success")
                }
                .addOnFailureListener {
                    Log.e("ERROR", "GET_ERROR_addDuaRequest->$it")

                    onResult("${it.message}")
                }
        }
    }

    override suspend fun makeDua(deeplinkRequest: DeeplinkRequest, onResult: (String) -> Unit) {
        database.collection("deeplinks")
            .document(deeplinkRequest.deeplink)
            .set(deeplinkRequest)
            .addOnSuccessListener {
                onResult("Success")
            }
            .addOnFailureListener {
                throw IllegalArgumentException(it.message.toString())
            }
    }

    override fun getDuaOfReceiverFromDeeplink(
        deeplink: String,
        onResult: (DeeplinkRequestState) -> Unit
    ) {
        database.collection("deeplinks")
            .document(deeplink)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    Log.e("SUCCESS", "GET_SUCCESS->$value\n ${value.data}")
                    onResult(DeeplinkRequestState(response = value.toObject()))
                }
                if (error != null) {
                    onResult(DeeplinkRequestState(error = error.message.toString()))
                    return@addSnapshotListener
                }
            }
    }
}