package com.ahrorovk.duaforpeople.main.presentation.requestScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.util.Resource
import com.ahrorovk.duaforpeople.core.domain.models.DeeplinkRequest
import com.ahrorovk.duaforpeople.main.domain.request.use_cases.AddDeeplinkRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val addDeeplinkRequestUseCase: AddDeeplinkRequestUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RequestState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        RequestState()
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

    fun onEvent(event: RequestEvent) {
        when (event) {
            is RequestEvent.OnDuaDescriptionChange -> {
                _state.update {
                    it.copy(
                        duaDescription = event.state
                    )
                }
            }

            is RequestEvent.OnNameChange->{
                _state.update {
                    it.copy(
                        name = event.state
                    )
                }
            }

            is RequestEvent.OnDeeplinkChange -> {
                _state.update {
                    it.copy(
                        deeplink = event.state
                    )
                }
                addDeeplinkRequest()
            }

            else -> {}
        }
    }

    private fun addDeeplinkRequest() {
        addDeeplinkRequestUseCase.invoke(
            DeeplinkRequest(
                _state.value.uid,
                "",
                _state.value.deeplink,
                _state.value.duaDescription,
                _state.value.name
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            answer = result.message.toString()
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
                            answer = result.data.toString()
                        )
                    }

                    Log.e("Success", "AddDeeplinkRequestSuccess${result.data}")
                }
            }
        }.launchIn(viewModelScope)
    }
}