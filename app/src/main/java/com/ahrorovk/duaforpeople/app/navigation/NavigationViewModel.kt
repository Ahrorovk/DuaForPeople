package com.ahrorovk.duaforpeople.app.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        NavigationState()
    )

    fun onEvent(event: NavigationEvent) {
        when (event) {
            else -> Unit
        }
    }
}