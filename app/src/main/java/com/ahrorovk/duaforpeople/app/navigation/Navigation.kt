package com.ahrorovk.duaforpeople.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahrorovk.duaforpeople.app.navigation.components.DuaForPeopleBottomBar
import com.ahrorovk.duaforpeople.app.navigation.graph.CallNavGraph
import com.ahrorovk.duaforpeople.core.presentation.components.CustomIconButton
import com.ahrorovk.duaforpeople.core.util.Graph
import com.ahrorovk.duaforpeople.core.util.Routes
import com.ahrorovk.duaforpeople.core.util.doesScreenHaveBottomBar
import com.ahrorovk.duaforpeople.core.util.doesScreenHavePopBack
import com.ahrorovk.duaforpeople.core.util.doesScreenHaveTopBar
import com.ahrorovk.duaforpeople.core.util.getTopBarTitle
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    state: NavigationState,
    onEvent: (NavigationEvent) -> Unit
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val scaffoldState = rememberScaffoldState()
    val topBarTitle = remember { mutableStateOf("") }

    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    ModalBottomSheetLayout(bottomSheetNavigator) {
        Scaffold(
            bottomBar = {
                if (doesScreenHaveBottomBar(currentScreen)) {
                    DuaForPeopleBottomBar(navController)
                }
            },
            topBar = {
                if (doesScreenHaveTopBar(currentScreen))
                    TopAppBar(title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getTopBarTitle(currentScreen).ifEmpty { topBarTitle.value },
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                        navigationIcon = {
                            if (doesScreenHavePopBack(currentScreen)) {
                                CustomIconButton(icon = Icons.Filled.KeyboardArrowLeft) {
                                    navController.popBackStack()
                                }
                            }
                        },
                        actions = {
                            if (currentScreen == Routes.UserScreen.route) {
                                CustomIconButton(icon = Icons.Default.ExitToApp) {
//                                    onEvent(NavigationEvent.Clear)
                                    navController.navigate(Routes.AuthorizationScreen.route) {
                                        popUpTo(Routes.UserScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    )
            }
        ) { it ->
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Graph.MainGraph.route
            ) {
                CallNavGraph(navController, topBarTitle, scaffoldState)
            }
        }
    }
}