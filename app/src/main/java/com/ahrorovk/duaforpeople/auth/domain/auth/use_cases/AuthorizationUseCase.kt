package com.ahrorovk.duaforpeople.auth.domain.auth.use_cases

import com.ahrorovk.duaforpeople.auth.domain.AuthRepository
import com.ahrorovk.duaforpeople.core.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun invoke(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = repository.loginUser(email, password)
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}