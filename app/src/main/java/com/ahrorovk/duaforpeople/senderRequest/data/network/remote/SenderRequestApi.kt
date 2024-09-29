package com.ahrorovk.duaforpeople.senderRequest.data.network.remote

import com.ahrorovk.duaforpeople.core.util.Constants
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMMessage
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SenderRequestApi {
    @Headers("Content-Type: application/json")
    @POST("v1/projects/${Constants.FIREBASE_PROJECT_ID}/messages:send")
    suspend fun sendMessage(
        @Header("Authorization") authorization: String,
        @Body fcmMessage: FCMMessage
    ): FCMResponse
}