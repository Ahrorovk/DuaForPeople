package com.ahrorovk.duaforpeople.main.domain.request.use_cases

import androidx.compose.runtime.mutableStateOf
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.main.domain.MainRepository
import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddDeeplinkRequestUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(deeplinkRequest: DeeplinkRequest): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading<String>())
                val state = mutableStateOf("")
                repository.addDeeplinkRequest(deeplinkRequest) { response ->
                    state.value = response
                }
                delay(1000)
                if (state.value == "Success") {
                    emit(Resource.Success<String>(state.value))
                } else {
                    emit(Resource.Error<String>("Error->${state.value}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error<String>("MessageError->${e.message}"))
            }
        }
    }
}