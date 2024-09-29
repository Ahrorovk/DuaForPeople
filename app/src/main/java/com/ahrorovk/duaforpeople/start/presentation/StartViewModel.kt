package com.ahrorovk.duaforpeople.start.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        StartState()
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
}