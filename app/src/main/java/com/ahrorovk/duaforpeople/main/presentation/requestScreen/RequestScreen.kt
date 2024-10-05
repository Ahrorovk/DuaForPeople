package com.ahrorovk.duaforpeople.main.presentation.requestScreen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomButton
import com.ahrorovk.duaforpeople.core.presentation.components.CustomTextField
import com.ahrorovk.duaforpeople.core.util.Constants
import java.util.UUID

@Composable
fun RequestScreen(
    state: RequestState,
    onEvent: (RequestEvent) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                value = state.name,
                onValueChange = {
                    onEvent(RequestEvent.OnNameChange(it))
                },
                hint = "Your name"
            )

            Spacer(modifier = Modifier.padding(12.dp))

            CustomTextField(
                value = state.duaDescription,
                onValueChange = {
                    onEvent(RequestEvent.OnDuaDescriptionChange(it))
                },
                hint = "Your dua"
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    state.deeplink,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )

                if (state.deeplink.isNotEmpty()) {
                    IconButton({
                        val share = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                Constants.BASE_URL + state.deeplink
                            )
                        }
                        context.startActivity(share)
                    }) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
                    }
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))

            CustomButton(
                text = "Generate token",
                textSize = 18,
                isLoading = false
            ) {
                onEvent(
                    RequestEvent.OnDeeplinkChange("${UUID.randomUUID()}")
                )
            }
        }
    }
}