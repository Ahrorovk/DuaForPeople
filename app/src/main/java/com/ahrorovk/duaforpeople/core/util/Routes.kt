package com.ahrorovk.duaforpeople.core.util

sealed class Routes(val route: String) {
    object RegistrationScreen : Routes("RegistrationScreen")
    object SettingsScreen : Routes("SettingsScreen")
    object AuthorizationScreen : Routes("AuthorizationScreen")
    object MyApplicationScreen : Routes("MyApplicationScreen")
    object MainScreen : Routes("MainScreen")
    object UserScreen : Routes("UserScreen")
    object TestScreen : Routes("TestScreen")
    object StartScreen : Routes("StartScreen")
    object RequestScreen : Routes("RequestScreen")
    object SenderRequestScreen : Routes("SenderRequestScreen")
}

sealed class BottomSheets(val route: String){

}
sealed class BottomSheetApplyOneTimeEvent {
    object CloseBottomSheet : BottomSheetApplyOneTimeEvent()
}