package com.ahrorovk.duaforpeople.link.data.network

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.link.domain.LinkRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.delay
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
) : LinkRepository {
    override val database: FirebaseFirestore = Firebase.firestore

    override suspend fun getDeepLinksByUid(uid: String): List<DeeplinkRequest> {
        val links = mutableListOf<DeeplinkRequest>()
        database.collection("deeplinks")
            .whereEqualTo("uidReceiver", uid)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    links.addAll(value.toObjects())
                }
                if (error != null) {
                    throw IllegalArgumentException(error.message.toString())
                }
            }
        delay(1000)
        return links.toList()
    }

}