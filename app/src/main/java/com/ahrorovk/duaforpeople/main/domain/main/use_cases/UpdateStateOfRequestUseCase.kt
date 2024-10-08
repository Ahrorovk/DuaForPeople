package com.ahrorovk.duaforpeople.main.domain.main.use_cases

import android.util.Log
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.main.domain.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateStateOfRequestUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(duaRequest: DuaRequest): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                repository.updateStateOfRequest(duaRequest) {
                }
                delay(1000)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}