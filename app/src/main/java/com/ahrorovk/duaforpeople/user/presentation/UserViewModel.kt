package com.ahrorovk.duaforpeople.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.user.domain.models.UserModel
import com.ahrorovk.duaforpeople.user.domain.states.UserModelState
import com.ahrorovk.duaforpeople.user.domain.use_cases.AddLimitsUseCase
import com.ahrorovk.duaforpeople.user.domain.use_cases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val addLimitsUseCase: AddLimitsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        UserState()
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

    fun onEvent(event: UserEvent) {
        when (event) {
            UserEvent.AddLimits -> {
                addLimits()
            }

            UserEvent.GetUserById -> {
                getUserById()
            }

            is UserEvent.OnLimitChange -> {
                _state.update {
                    it.copy(
                        limit = event.limit
                    )
                }
            }

            else -> {}
        }
    }

    private fun addLimits() {
        addLimitsUseCase.invoke(UserModel(_state.value.uid, _state.value.limit)).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e("Error", "AddLimitsError-> ${result.message}")
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(isLoading = true)
                        )
                    }
                }

                is Resource.Success -> {
                    Log.e("Success", "AddLimitsSuccess-> ${result.data}")
                    val response = result.data
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(response = response),
                            limit = response?.limit ?: 0
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserById() {
        getUserByIdUseCase.invoke(_state.value.uid).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e("Error", "getUserByIdError-> ${result.message}")
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(isLoading = true)
                        )
                    }
                }

                is Resource.Success -> {
                    Log.e("Success", "getUserByIdSuccess-> ${result.data}")
                    val response = result.data
                    _state.update {
                        it.copy(
                            userModelState = UserModelState(response = response),
                            limit = response?.limit ?: 0
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}