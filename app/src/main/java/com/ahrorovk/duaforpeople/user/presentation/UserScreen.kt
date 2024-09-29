package com.ahrorovk.duaforpeople.user.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomButton
import com.ahrorovk.duaforpeople.core.presentation.components.CustomTextField

@Composable
fun UserScreen(
    state: UserState,
    onEvent: (UserEvent) -> Unit
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Uid: ")
                Text(state.uid)
            }
            Spacer(Modifier.padding(12.dp))


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Requests limit: ")

                CustomTextField(
                    value = state.limit.toString(),
                    onValueChange = {
                        if (it.isEmpty())
                            onEvent(UserEvent.OnLimitChange(0))
                        else
                            onEvent(UserEvent.OnLimitChange(it.toInt()))
                    },
                    hint = "Limit",
                    keyboardType = KeyboardType.Number
                )
            }

            Spacer(Modifier.padding(12.dp))

            CustomButton(
                text = "Save Changes",
                textSize = 18,
                isLoading = state.userModelState.isLoading,
                modifier = Modifier.padding(10.dp)
            ) {
                onEvent(UserEvent.AddLimits)
            }
        }
    }
}