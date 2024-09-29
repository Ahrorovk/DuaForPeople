package com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DeeplinkRequestState
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.main.domain.main.use_cases.GetDuaRequestsByUidUseCase
import com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases.AddDuaRequestUseCase
import com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases.GetDuaOfReceiverFromDeeplinkUseCase
import com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases.MakeDuaUseCase
import com.ahrorovk.duaforpeople.user.domain.use_cases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SenderRequestViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val addDuaRequestUseCase: AddDuaRequestUseCase,
    private val getDuaOfReceiverFromDeeplinkUseCase: GetDuaOfReceiverFromDeeplinkUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getDuaRequestsByUidUseCase: GetDuaRequestsByUidUseCase,
    private val makeDuaUseCase: MakeDuaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SenderRequestState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SenderRequestState()
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

    fun onEvent(event: SenderRequestEvent) {
        when (event) {
            SenderRequestEvent.AddDuaRequest -> {
                addDuaRequest()
            }

            is SenderRequestEvent.OnDeeplinkChange -> {
                _state.update {
                    it.copy(
                        deeplink = event.id
                    )
                }
            }

            is SenderRequestEvent.OnDuaFieldChange -> {
                _state.update {
                    it.copy(
                        dua = event.state
                    )
                }
            }

            is SenderRequestEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.state
                    )
                }
            }

            SenderRequestEvent.MakeDua -> {
                makeDua()
            }

            SenderRequestEvent.GetUserById -> {
                getUserById()
                getDuaRequestsByUid()
            }

            SenderRequestEvent.GetDuaOfReceiverFromDeeplink -> {
                getDuaOfReceiverFromDeeplink()
            }

            else -> Unit
        }
    }


    private fun makeDua() {

        makeDuaUseCase.invoke(
            _state.value.deeplinkRequest.copy(quantities = _state.value.deeplinkRequest.quantities + 1)
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            response = result.message.toString(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    getDuaOfReceiverFromDeeplink()
                    Log.e("MakeDuaSuccess", "MakeDuaSuccess->${result.data}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getDuaRequestsByUid() {
        viewModelScope.launch {
            getDuaRequestsByUidUseCase.invoke(_state.value.uid)
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            Log.e(
                                "Error",
                                "ErrorDuaRequestsByUid->${result.message}"
                            )
                            _state.update {
                                it.copy(
                                    response = result.data?.error.toString(),
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            val response = result.data
                            Log.e("Success", "SuccessDuaRequestsByUid->${response}")

                            response?.let { res ->
                                _state.update { it_ ->
                                    res.response?.count {
                                        it.uidReceiver == _state.value.uid
                                    }?.let {
                                        it_.copy(
                                            quantities = it,
                                            isLoading = false
                                        )
                                    }!!
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getUserById() {
        Log.e("UidReceiver", "uidReceiver-> ${_state.value.uidReceiver}")
        getUserByIdUseCase.invoke(_state.value.uidReceiver).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e("Error", "getUserByIdError-> ${result.message}")
                    _state.update {
                        it.copy(
                            response = result.message.toString(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Resource.Success -> {
                    Log.e("Success", "getUserByIdSuccess-> ${result.data}")
                    val response = result.data
                    _state.update {
                        it.copy(
                            limit = response?.limit ?: 0,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addDuaRequest() {
        addDuaRequestUseCase.invoke(
            DuaRequest(
                _state.value.name,
                _state.value.dua,
                false,
                _state.value.uidReceiver,
                _state.value.uid,
                _state.value.deeplinkRequestState.response?.fcmTokenSender ?: ""
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e("Error", "addDuaRequestUseCaseError-> ${result.message}")
                    _state.update {
                        it.copy(
                            response = result.message.toString(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Resource.Success -> {
                    Log.e("Success", "${result.data}")
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getDuaOfReceiverFromDeeplink() {
        getDuaOfReceiverFromDeeplinkUseCase
            .invoke(_state.value.deeplink)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.e("Error", "getDuaOfReceiverFromDeeplinkError->${result.message}")
                        _state.update {
                            it.copy(
                                response = result.message.toString(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { res ->
                            Log.e("Success", "getDuaOfReceiverFromDeeplinkSuccess->$res")
                            _state.update {
                                res.response?.let { response ->
                                    it.copy(
                                        deeplinkRequest = response,
                                        uidReceiver = response.uidReceiver,
                                        isLoading = false
                                    )
                                }!!
                            }
                            getUserById()
                            getDuaRequestsByUid()
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}