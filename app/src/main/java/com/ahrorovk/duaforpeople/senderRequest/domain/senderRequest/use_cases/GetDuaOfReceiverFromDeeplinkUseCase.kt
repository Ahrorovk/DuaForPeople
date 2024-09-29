package com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases

import androidx.compose.runtime.mutableStateOf
import com.ahrorovk.duaforpeople.core.domain.states.DeeplinkRequestState
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDuaOfReceiverFromDeeplinkUseCase @Inject constructor(
    private val repository: SenderRequestRepository
) {
    operator fun invoke(deeplink: String): Flow<Resource<DeeplinkRequestState>> {
        return flow {
            try {
                Resource.Loading<String>()
                val state = mutableStateOf(DeeplinkRequestState())
                repository.getDuaOfReceiverFromDeeplink(deeplink) {
                    state.value = it
                }
                delay(500)
                emit(Resource.Success<DeeplinkRequestState>(state.value))
            } catch (e: Exception) {
                emit(Resource.Error<DeeplinkRequestState>(e.message.toString()))
            }
        }
    }
}