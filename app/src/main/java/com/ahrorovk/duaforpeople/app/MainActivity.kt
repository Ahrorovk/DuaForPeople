package com.ahrorovk.duaforpeople.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrorovk.duaforpeople.app.navigation.Navigation
import com.ahrorovk.duaforpeople.app.navigation.NavigationViewModel
import com.ahrorovk.duaforpeople.core.data.local.DataStoreManager
import com.ahrorovk.duaforpeople.core.presentation.ui.theme.DuaForPeopleTheme
import com.ahrorovk.duaforpeople.core.util.Constants
import com.ahrorovk.duaforpeople.service.DuaForPeopleFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val duaForPeopleFirebaseMessagingService = DuaForPeopleFirebaseMessagingService()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(applicationContext)
        duaForPeopleFirebaseMessagingService.onCreate()
        setContent {
            LaunchedEffect(Constants.FCM_TOKEN) {
                if (Constants.FCM_TOKEN.isNotEmpty()) {
                    Log.e("HELLO","->${Constants.FCM_TOKEN}")
                    dataStoreManager.updateFcmTokenKey(Constants.FCM_TOKEN)
                }
            }
            DuaForPeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = hiltViewModel<NavigationViewModel>()
                    val state = viewModel.state.collectAsState()
                    Navigation(
                        modifier = Modifier.padding(innerPadding),
                        state = state.value
                    ) { event ->
                        when (event) {
                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
                }
            }
        }
    }
}