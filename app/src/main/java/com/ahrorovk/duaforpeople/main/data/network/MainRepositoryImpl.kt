package com.ahrorovk.duaforpeople.main.data.network

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DuaRequestState
import com.ahrorovk.duaforpeople.main.domain.MainRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
) : MainRepository {
    override val database = Firebase.firestore

    override fun getDuaRequestsByUid(uid: String, onResult: (DuaRequestState) -> Unit) {
        database.collection("duaRequests")
            .whereEqualTo("uidReceiver", uid)
            .whereEqualTo("done", false)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    onResult(DuaRequestState(response = value.toObjects()))
                    return@addSnapshotListener
                }
                if (error != null) {
                    onResult(DuaRequestState(error = error.message.toString()))
                    return@addSnapshotListener
                }
            }
    }

    override fun updateStateOfRequest(duaRequest: DuaRequest, onResult: (String) -> Unit) {
        database.collection("duaRequests")
            .document(duaRequest.id)
            .set(duaRequest)
            .addOnSuccessListener {
                onResult("Success")
            }
            .addOnFailureListener {
                throw IllegalArgumentException(it.message.toString())
            }
    }

    override fun addDeeplinkRequest(deeplinkRequest: DeeplinkRequest, onResult: (String) -> Unit) {
        if (deeplinkRequest.uidReceiver.isNotEmpty()) {
            database.collection("deeplinks")
                .document(deeplinkRequest.deeplink)
                .set(deeplinkRequest)
                .addOnSuccessListener {
                    onResult("Success")
                }
                .addOnFailureListener {
                    onResult("${it.message}")
                }
        }
    }
}