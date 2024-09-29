package com.ahrorovk.duaforpeople.user.data.network

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.ahrorovk.duaforpeople.user.domain.UserRepository
import com.ahrorovk.duaforpeople.user.domain.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
) : UserRepository {

    override val database: FirebaseFirestore = Firebase.firestore

    override suspend fun addLimits(userModel: UserModel): UserModel {
        val state = mutableStateOf(UserModel())
        database.collection("limits")
            .document(userModel.uid)
            .set(userModel)
            .addOnSuccessListener {
                database.collection("limits")
                    .document(userModel.uid)
                    .addSnapshotListener { value, error ->
                        if (value != null) {
                            state.value = value.toObject<UserModel>()!!
                        }
                        if (error != null) {
                            throw IllegalArgumentException(error.message.toString())
                        }
                    }
            }
        delay(1000)
        Log.e("SUCCESS", "STATE->${state.value}")
        return state.value
    }

    override suspend fun getUserById(uid: String): UserModel? {
        val state = mutableStateOf<UserModel?>(null)
        database.collection("limits")
            .document(uid)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    state.value = value.toObject<UserModel>()
                }
                if (error != null) {
                    throw IllegalArgumentException(error.message.toString())
                }
            }
        delay(1000)
        return state.value
    }
}