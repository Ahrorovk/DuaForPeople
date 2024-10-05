package com.ahrorovk.duaforpeople.senderRequest.data.network.remote

import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMResponse
import com.ahrorovk.duaforpeople.senderRequest.data.model.Notification
import retrofit2.http.Body
import retrofit2.http.POST

interface SenderRequestApi {
    @POST("send")
    suspend fun sendMessage(
        @Body fcmMessage: Notification
    ): FCMResponse
}