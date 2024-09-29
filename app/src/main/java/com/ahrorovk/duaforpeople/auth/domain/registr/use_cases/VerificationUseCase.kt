package com.ahrorovk.duaforpeople.auth.domain.registr.use_cases

import com.ahrorovk.duaforpeople.auth.domain.AuthRepository
import com.ahrorovk.duaforpeople.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerificationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun invoke(email: String): Flow<Resource<Void?>> {
        return flow {
            emit(Resource.Loading())
            val result = repository.verifyEmail(email)
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}