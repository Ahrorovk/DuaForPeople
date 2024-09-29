package com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MakeDuaUseCase @Inject constructor(
    private val repository: SenderRequestRepository
) {
    operator fun invoke(deeplinkRequest: DeeplinkRequest): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                repository.makeDua(deeplinkRequest) {

                }
                delay(1000)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}