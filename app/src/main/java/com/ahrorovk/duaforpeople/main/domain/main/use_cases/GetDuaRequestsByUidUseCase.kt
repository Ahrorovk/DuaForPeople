package com.ahrorovk.duaforpeople.main.domain.main.use_cases

import androidx.compose.runtime.mutableStateOf
import com.ahrorovk.duaforpeople.core.domain.states.DuaRequestState
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.main.domain.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDuaRequestsByUidUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(uid: String): Flow<Resource<DuaRequestState>> {
        return flow {
            try {
                emit(Resource.Loading<DuaRequestState>())
                val state = mutableStateOf(DuaRequestState())
                repository.getDuaRequestsByUid(uid) { response ->
                    state.value = response
                }
                delay(2000)
                if (state.value.response != null) {
                    emit(Resource.Success<DuaRequestState>(state.value))
                }
            } catch (e: Exception) {
                emit(Resource.Error<DuaRequestState>(e.message.toString()))
            }
        }
    }
}