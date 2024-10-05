package com.ahrorovk.duaforpeople.link.presentation.linkScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ahrorovk.duaforpeople.core.presentation.components.CustomProgressIndicator
import com.ahrorovk.duaforpeople.link.presentation.linkScreen.components.LinkItem

@Composable
fun LinkScreen(
    state: LinkState,
    onEvent: (LinkEvent) -> Unit
) {
    val context = LocalContext.current
    CustomProgressIndicator(state.deeplinkRequestState.isLoading)
    Box(modifier = Modifier.fillMaxSize()) {
        state.deeplinkRequestState.response?.let { response ->
            LazyColumn {
                items(response) { link ->
                    LinkItem(Modifier, link, context)
                }
            }
        }
    }
}