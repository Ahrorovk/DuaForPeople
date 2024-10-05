package com.ahrorovk.duaforpeople.main.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomButton
import com.ahrorovk.duaforpeople.core.presentation.components.CustomProgressIndicator
import com.ahrorovk.duaforpeople.main.presentation.mainScreen.components.CustomRequestCheckItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {

    val swipeRefreshState = rememberSwipeRefreshState(state.stateOfRequests.isLoading)

    LaunchedEffect(Unit) {
        if (state.stateOfRequests.response == null)
            onEvent(MainEvent.GetDuaRequestsByUid)
    }

    CustomProgressIndicator(state.stateOfRequests.isLoading)

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.padding(bottom = if (state.stateOfRequests.response?.isNotEmpty() == true) 100.dp else 0.dp),
            onClick = {
                onEvent(MainEvent.GoToRequest)
            },
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) { it ->
        state.stateOfRequests.response?.let { response ->
            if (response.isNotEmpty())
                SwipeRefresh(
                    modifier = Modifier.fillMaxSize(),
                    state = swipeRefreshState,
                    onRefresh = {
                        onEvent(MainEvent.GetDuaRequestsByUid)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn {
                            itemsIndexed(response) { ind, request ->
                                CustomRequestCheckItem(
                                    isDone = request.done,
                                    dua = request.dua,
                                    senderName = request.name
                                ) {
                                    onEvent(
                                        MainEvent.OnIsDoneChange(
                                            ind,
                                            request.copy(done = !request.done)
                                        )
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.padding(60.dp))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                CustomButton(
                                    text = "Save", textSize = 18, isLoading = false,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    onEvent(MainEvent.OnSaveChanges)
                                }
                            }
                        }
                    }
                }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "You haven't any dua requests",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}