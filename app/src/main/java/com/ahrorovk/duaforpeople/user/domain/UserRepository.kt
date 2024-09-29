package com.ahrorovk.duaforpeople.user.domain

import com.ahrorovk.duaforpeople.user.domain.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore

interface UserRepository {

    val database: FirebaseFirestore

    suspend fun addLimits(userModel: UserModel): UserModel

    suspend fun getUserById(uid: String): UserModel?
}