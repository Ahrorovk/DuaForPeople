package com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases

import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.senderRequest.data.model.FCMResponse
import com.ahrorovk.duaforpeople.senderRequest.data.model.Notification
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendPushNotificationUseCase @Inject constructor(
    private val repository: SenderRequestRepository
) {
    operator fun invoke(notification: Notification): Flow<Resource<FCMResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = repository.sendMessage(notification)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}