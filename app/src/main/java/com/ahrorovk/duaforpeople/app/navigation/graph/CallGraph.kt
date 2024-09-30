package com.ahrorovk.duaforpeople.app.navigation.graph

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen.AuthorizationEvent
import com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen.AuthorizationScreen
import com.ahrorovk.duaforpeople.auth.presentation.authorizationScreen.AuthorizationViewModel
import com.ahrorovk.duaforpeople.auth.presentation.registrationScreen.RegistrationEvent
import com.ahrorovk.duaforpeople.auth.presentation.registrationScreen.RegistrationScreen
import com.ahrorovk.duaforpeople.auth.presentation.registrationScreen.RegistrationViewModel
import com.ahrorovk.duaforpeople.core.util.Constants
import com.ahrorovk.duaforpeople.core.util.Graph
import com.ahrorovk.duaforpeople.core.util.Routes
import com.ahrorovk.duaforpeople.main.presentation.mainScreen.MainEvent
import com.ahrorovk.duaforpeople.main.presentation.mainScreen.MainScreen
import com.ahrorovk.duaforpeople.main.presentation.mainScreen.MainViewModel
import com.ahrorovk.duaforpeople.main.presentation.requestScreen.RequestScreen
import com.ahrorovk.duaforpeople.main.presentation.requestScreen.RequestViewModel
import com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen.SenderRequestEvent
import com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen.SenderRequestScreen
import com.ahrorovk.duaforpeople.senderRequest.presentation.senderRequestScreen.SenderRequestViewModel
import com.ahrorovk.duaforpeople.start.presentation.StartEvent
import com.ahrorovk.duaforpeople.start.presentation.StartScreen
import com.ahrorovk.duaforpeople.start.presentation.StartViewModel
import com.ahrorovk.duaforpeople.user.presentation.UserEvent
import com.ahrorovk.duaforpeople.user.presentation.UserScreen
import com.ahrorovk.duaforpeople.user.presentation.UserViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.CallNavGraph(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    navigation(
        route = Graph.MainGraph.route,
        startDestination = Routes.StartScreen.route
    ) {

        composable(Routes.StartScreen.route) {
            val viewModel = hiltViewModel<StartViewModel>()
            val state = viewModel.state.collectAsState()
            StartScreen(state = state.value) { event ->
                when (event) {
                    StartEvent.GoToAuthorization -> {
                        navController.navigate(Routes.AuthorizationScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    StartEvent.GoToMain -> {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }

        composable(Routes.AuthorizationScreen.route) {
            val viewModel = hiltViewModel<AuthorizationViewModel>()
            val state = viewModel.state.collectAsState()
            AuthorizationScreen(state = state.value) { event ->
                when (event) {
                    is AuthorizationEvent.GoToRegistration -> {
                        navController.navigate(Routes.RegistrationScreen.route) {
                            popUpTo(Routes.AuthorizationScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    is AuthorizationEvent.GoToMainScreen -> {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.AuthorizationScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
        }
        composable(Routes.RegistrationScreen.route) {
            val viewModel = hiltViewModel<RegistrationViewModel>()
            val state = viewModel.state.collectAsState()

            RegistrationScreen(state = state.value) { event ->
                when (event) {
                    RegistrationEvent.GoToAuthorization -> {
                        navController.navigate(Routes.AuthorizationScreen.route) {
                            popUpTo(Routes.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    RegistrationEvent.GoToMainScreen -> {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
        }
        composable(
            Routes.SenderRequestScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${Constants.BASE_URL}{${Constants.DEEPLINK_ARG}}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument(Constants.DEEPLINK_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val deeplink = backStackEntry.arguments?.getString(Constants.DEEPLINK_ARG) ?: ""
            val viewModel = hiltViewModel<SenderRequestViewModel>()
            val state = viewModel.state.collectAsState()
            LaunchedEffect(Unit) {
                viewModel.onEvent(SenderRequestEvent.OnDeeplinkChange(deeplink))
                if (deeplink.isNotEmpty())
                    viewModel.onEvent(SenderRequestEvent.GetDuaOfReceiverFromDeeplink)
            }
            SenderRequestScreen(state.value) { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            }
        }
        composable(Routes.MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            val state = viewModel.state.collectAsState()
            MainScreen(
                state = state.value
            ) { event ->
                when (event) {
                    MainEvent.GoToRequest -> {
                        navController.navigate(Routes.RequestScreen.route)
                    }

                    else -> viewModel.onEvent(event)
                }
            }
        }
        composable(Routes.RequestScreen.route) {
            val viewModel = hiltViewModel<RequestViewModel>()
            val state = viewModel.state.collectAsState()
            RequestScreen(state = state.value) { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            }
        }

        composable(Routes.UserScreen.route) {
            val viewModel = hiltViewModel<UserViewModel>()
            val state = viewModel.state.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.onEvent(UserEvent.GetUserById)
            }
            UserScreen(state.value) { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            }
        }
    }
}