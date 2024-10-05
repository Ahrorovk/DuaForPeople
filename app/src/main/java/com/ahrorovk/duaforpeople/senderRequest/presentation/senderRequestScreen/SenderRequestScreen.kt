package com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomButton
import com.ahrorovk.duaforpeople.core.presentation.components.CustomTextField
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SenderRequestScreen(
    state: SenderRequestState,
    onEvent: (SenderRequestEvent) -> Unit
) {
    val context = LocalContext.current
    val swipeRefreshState = rememberSwipeRefreshState(state.isLoading)

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = {
//            onEvent(SenderRequestEvent.GetUserById)
            if (state.deeplink.isNotEmpty())
                onEvent(SenderRequestEvent.GetDuaOfReceiverFromDeeplink)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTextField(
                    value = state.deeplink,
                    hint = "deeplink",
                    onValueChange = {
                    },
                    isAvailable = false
                )

                Spacer(modifier = Modifier.padding(12.dp))

                if (state.deeplinkRequest.duaDescription.isNotEmpty())
                    state.deeplinkRequest.let { request ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text("Name: ${request.name}")

                                Spacer(modifier = Modifier.padding(8.dp))

                                Text("Dua: ${request.duaDescription}")

                                CustomButton(
                                    text = "Make dua",
                                    textSize = 18,
                                    isLoading = false,
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 24.dp
                                    )
                                ) {
                                    if (state.name.isNotEmpty())
                                        onEvent(SenderRequestEvent.MakeDua)
                                    else
                                        Toast.makeText(
                                            context,
                                            "Please fill in your name",
                                            Toast.LENGTH_LONG
                                        ).show()
                                }
                            }
                        }
                    }

                Spacer(modifier = Modifier.padding(12.dp))

                Text(
                    "Also you can ask dua from this person",
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.name, onValueChange = {
                        onEvent(SenderRequestEvent.OnNameChange(it))
                    }, hint = "Your name"
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.dua, onValueChange = {
                        onEvent(SenderRequestEvent.OnDuaFieldChange(it))
                    }, hint = "Your dua"
                )

                Spacer(modifier = Modifier.padding(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        text = "Ask dua",
                        textSize = 18,
                        isLoading = false,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        if (state.limit > state.quantities)
                            onEvent(SenderRequestEvent.AddDuaRequest)
                        else {
                            Toast.makeText(
                                context,
                                "${state.deeplinkRequest.name}`s request limit has been reached",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}