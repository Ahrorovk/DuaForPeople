package com.ahrorovk.duaforpeople.auth.presentation.registrationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.auth.domain.registr.states.RegistrResponseState
import com.ahrorovk.duaforpeople.auth.domain.registr.use_cases.RegistrationUseCase
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
class RegistrationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RegistrationState()
    )

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is RegistrationEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            RegistrationEvent.Registration -> {
                registration()
            }

            else -> Unit
        }
    }

    private fun registration() {
        viewModelScope.launch {
            registrationUseCase.invoke(
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
                                registrationState = RegistrResponseState(
                                    responseState = response
                                )
                            )
                        }
                        Log.v("Success", "registrationSuccess: ${response?.user?.uid}")
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                registrationState = RegistrResponseState(
                                    error = result.message ?: "Unknown error"
                                )
                            )
                        }
                        Log.e("Error", "registrationError: ${result.message}")
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                registrationState = RegistrResponseState(
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