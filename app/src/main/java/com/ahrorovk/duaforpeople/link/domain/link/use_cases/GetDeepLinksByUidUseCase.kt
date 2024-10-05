package com.ahrorovk.duaforpeople.link.domain.link.use_cases

import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.link.domain.LinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDeepLinksByUidUseCase @Inject constructor(
    private val repository: LinkRepository
) {
    operator fun invoke(uid: String): Flow<Resource<List<DeeplinkRequest>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = repository.getDeepLinksByUid(uid)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}