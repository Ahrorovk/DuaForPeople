package com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.auth.domain.auth.states.AuthResponseState
import com.ahrorovk.duaforpeople.auth.domain.auth.use_cases.AuthorizationUseCase
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authorizationUseCase: AuthorizationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AuthorizationState()
    )

    fun onEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is AuthorizationEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            AuthorizationEvent.Authorization -> {
                authorization()
            }

            else -> Unit
        }
    }

    private fun authorization() {
        viewModelScope.launch {
            authorizationUseCase.invoke(
                _state.value.email,
                _state.value.password
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val response = result.data
                        viewModelScope.launch {
                            dataStoreManager.updateAccessToken(
                                (response?.user?.uid ?: 0).toString()
                            )
                        }
                        _state.update {
                            it.copy(
                                authResponseState = AuthResponseState(
                                    responseState = response
                                )
                            )
                        }
                        Log.e("TAG", "onEventSuccess: ${response?.user?.uid}")
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                authResponseState = AuthResponseState(
                                    error = result.message ?: "Unknown error"
                                )
                            )
                        }
                        Log.e("TAG", "onEventError: ${result.message}")
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                authResponseState = AuthResponseState(
                                    isLoading = true
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}