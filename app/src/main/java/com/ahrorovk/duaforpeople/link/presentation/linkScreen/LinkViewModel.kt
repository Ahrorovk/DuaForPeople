package com.ahrorovk.duaforpeople.link.presentation.linkScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.link.domain.link.states.DeeplinkRequestsState
import com.ahrorovk.duaforpeople.link.domain.link.use_cases.GetDeepLinksByUidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LinkViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getDeepLinksByUidUseCase: GetDeepLinksByUidUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LinkState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        LinkState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    uid = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LinkEvent) {

        when (event) {
            LinkEvent.GetDeepLinksByUid -> {
                getDeepLinksByUid()
            }

            else -> {}
        }
    }

    private fun getDeepLinksByUid() {
        getDeepLinksByUidUseCase.invoke(_state.value.uid).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            deeplinkRequestState = DeeplinkRequestsState(error = result.message.toString())
                        )
                    }
                    Log.e(
                        "Error",
                        "ErrorGetDeepLinksByUid->${result.message}"
                    )
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            deeplinkRequestState = DeeplinkRequestsState(isLoading = true)
                        )
                    }
                }

                is Resource.Success -> {
                    val response = result.data
                    _state.update {
                        it.copy(
                            deeplinkRequestState = DeeplinkRequestsState(response = response)
                        )
                    }
                    Log.v(
                        "Success",
                        "SuccessGetDeepLinksByUid->${response}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}