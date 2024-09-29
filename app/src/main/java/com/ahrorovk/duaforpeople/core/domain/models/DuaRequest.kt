package com.ahrorovk.duaforpeople.core.domain.models

import androidx.room.ColumnInfo
import java.util.UUID

data class DuaRequest(
    val name: String = "Name",
    val dua: String = "Dua",
    val done: Boolean = false,
    @ColumnInfo("uid_receiver") val uidReceiver: String = UUID.randomUUID().toString(),
    @ColumnInfo("uid_sender") val uidSender: String = UUID.randomUUID().toString(),
    @ColumnInfo("fcm_token_sender") val fcmTokenSender: String = UUID.randomUUID().toString(),
    val id: String = "",
)
