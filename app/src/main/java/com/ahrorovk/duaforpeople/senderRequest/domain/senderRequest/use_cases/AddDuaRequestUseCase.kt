package com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases

import androidx.compose.runtime.mutableStateOf
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddDuaRequestUseCase @Inject constructor(
    private val repository: SenderRequestRepository
) {
    operator fun invoke(duaRequest: DuaRequest): Flow<Resource<String>> {
        return flow {
            Resource.Loading<String>()
            val state = mutableStateOf("")
            repository.addDuaRequest(duaRequest) {
                state.value = it
            }
            delay(500)
            if (state.value.contains("Success")) {
                emit(Resource.Success<String>(state.value))
            } else {
                emit(Resource.Error<String>("Error-> ${state.value}"))
            }
        }
    }
}