 package com.ahrorovk.duaforpeople.core.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.toMMDDYYYY(): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
    /*return DateTimeFormatter.ofPattern("MM/dd/yyyy")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))*/
}

fun Long.toMMMDD(): String {
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
    return sdf.format(Date(this))
    /*return DateTimeFormatter.ofPattern("MMM dd")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))*/
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toCurrentInMillis(): Long {
    val zonedDateTime = this.atStartOfDay(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}

fun Any.showLog(tag: String = "Hello") {
    Log.d(tag, this.toString())
}