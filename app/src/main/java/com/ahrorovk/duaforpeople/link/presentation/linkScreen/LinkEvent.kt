package com.ahrorovk.duaforpeople.link.presentation.linkScreen

sealed class LinkEvent {

    object GetDeepLinksByUid : LinkEvent()
}