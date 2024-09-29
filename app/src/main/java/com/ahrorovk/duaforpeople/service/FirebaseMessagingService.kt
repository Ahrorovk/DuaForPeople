package com.ahrorovk.duaforpeople.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import com.ahrorovk.duaforpeople.R
import com.ahrorovk.duaforpeople.app.MainActivity
import com.ahrorovk.duaforpeople.core.util.Constants.FCM_TOKEN
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

const val chanel_Name = "chanel_name"
const val chanel_id = "chanel_id"

class DuaForPeopleFirebaseMessagingService : FirebaseMessagingService() {
    private val serviceJob = Job()

    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    @ExperimentalMaterialNavigationApi
    @ExperimentalMaterial3Api
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
            Log.v("CloudMessage", "Notification ${remoteMessage.notification}")
            Log.v("CloudMessage", "Notification Title ${remoteMessage.notification!!.title}")
            Log.v("CloudMessage", "Notification Body ${remoteMessage.notification!!.body}")
        }
    }

    fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteView = RemoteViews("com.ahrorovk.duaforpeople", R.layout.notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, body)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.app_icon)
        return remoteView
    }

    @ExperimentalMaterialNavigationApi
    @ExperimentalMaterial3Api
    fun generateNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, chanel_id)
                .setSmallIcon(R.drawable.app_icon)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingintent)
        builder = builder.setContent(getRemoteView(title, body))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(chanel_id, chanel_Name, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            val token = task.result
            FCM_TOKEN = token
            Log.e("Hello", "FCM-Token -> \n $token")
        }
    }
}