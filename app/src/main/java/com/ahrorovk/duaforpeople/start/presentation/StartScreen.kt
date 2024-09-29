package com.ahrorovk.duaforpeople.start.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    state: StartState,
    onEvent: (StartEvent) -> Unit
) {
    LaunchedEffect(state.uid) {
        delay(1000)
        if (state.uid.isNotEmpty())
            onEvent(StartEvent.GoToMain)
        else onEvent(StartEvent.GoToAuthorization)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(40.dp))
    }
}