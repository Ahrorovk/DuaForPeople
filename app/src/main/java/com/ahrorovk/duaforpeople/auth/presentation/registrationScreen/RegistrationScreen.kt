package com.ahrorovk.duaforpeople.auth.presentation.registrationScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ahrorovk.duaforpeople.core.presentation.components.CustomButton
import com.ahrorovk.duaforpeople.core.presentation.components.CustomTextField

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.registrationState) {
        if (state.registrationState.responseState?.user != null) {
            onEvent(RegistrationEvent.GoToMainScreen)
        }
        if (state.registrationState.error.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = state.registrationState.error,
                actionLabel = "Try again",
                duration = SnackbarDuration.Indefinite
            )
        }
    }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomTextField(
                    value = state.email,
                    hint = "Email",
                    onValueChange = {
                        onEvent(RegistrationEvent.OnEmailChange(it))
                    }
                )

                Spacer(modifier = Modifier.padding(5.dp))

                CustomTextField(
                    value = state.password,
                    hint = "Password",
                    onValueChange = {
                        onEvent(RegistrationEvent.OnPasswordChange(it))
                    },
                    keyboardType = KeyboardType.Password
                )

                Spacer(modifier = Modifier.padding(5.dp))

                CustomButton(
                    text = "Registration",
                    textSize = 16,
                    color = MaterialTheme.colorScheme.onBackground,
                    isLoading = state.registrationState.isLoading,
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 24.dp
                    )
                ) {
                    onEvent(RegistrationEvent.Registration)
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already have an account?")
                    Text("Sign in",
                        modifier = Modifier
                            .clickable {
                                onEvent(RegistrationEvent.GoToAuthorization)
                            }
                            .padding(start = 5.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}