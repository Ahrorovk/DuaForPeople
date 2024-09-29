package com.ahrorovk.duaforpeople.user.domain.use_cases

import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.user.domain.UserRepository
import com.ahrorovk.duaforpeople.user.domain.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddLimitsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userModel: UserModel): Flow<Resource<UserModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = repository.addLimits(userModel)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}