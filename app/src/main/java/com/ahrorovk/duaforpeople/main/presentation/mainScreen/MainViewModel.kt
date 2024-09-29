package com.ahrorovk.duaforpeople.main.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.domain.models.DuaRequest
import com.ahrorovk.duaforpeople.core.domain.states.DuaRequestState
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.main.domain.main.use_cases.GetDuaRequestsByUidUseCase
import com.ahrorovk.duaforpeople.main.domain.main.use_cases.UpdateStateOfRequestUseCase
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
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getDuaRequestsByUidUseCase: GetDuaRequestsByUidUseCase,
    private val updateStateOfRequestUseCase: UpdateStateOfRequestUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MainState()
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

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnIsDoneChange -> {
                val requests =
                    (_state.value.stateOfRequests.response ?: emptyList()).toMutableList()
                requests[event.id] = event.request
                _state.update {
                    it.copy(
                        stateOfRequests = DuaRequestState(response = requests)
                    )
                }
            }

            MainEvent.OnSaveChanges -> {
                _state.value.stateOfRequests.response?.let { responses ->
                    responses.forEach { response ->
                        updateStateOfRequest(response)
                    }
                    getDuaRequestsByUid()
                }
            }

            MainEvent.GetDuaRequestsByUid -> {
                getDuaRequestsByUid()
            }

            else -> Unit
        }
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
                                    stateOfRequests = DuaRequestState(error = result.data?.error.toString())
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    stateOfRequests = DuaRequestState(isLoading = true)
                                )
                            }
                        }

                        is Resource.Success -> {
                            val response = result.data
                            Log.e("Success", "SuccessDuaRequestsByUid->${response}")

                            response?.let { res ->
                                _state.update { it_ ->
                                    it_.copy(stateOfRequests = res)
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun updateStateOfRequest(duaRequest: DuaRequest) {
        viewModelScope.launch {
            updateStateOfRequestUseCase.invoke(duaRequest)
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            Log.e(
                                "Error",
                                "ErrorUpdateStateOfRequest->${result.message}"
                            )
                            _state.update {
                                it.copy(
                                    stateOfRequests = DuaRequestState(error = result.message.toString())
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    stateOfRequests = DuaRequestState(isLoading = true)
                                )
                            }
                        }

                        is Resource.Success -> {
                            val response = result.data
                            Log.e("Success", "SuccessDuaRequestsByUid->${response}")
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
}