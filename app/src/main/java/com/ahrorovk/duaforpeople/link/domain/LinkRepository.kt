package com.ahrorovk.duaforpeople.link.domain

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.google.firebase.firestore.FirebaseFirestore

interface LinkRepository {

    val database: FirebaseFirestore

    suspend fun getDeepLinksByUid(uid: String): List<DeeplinkRequest>
}