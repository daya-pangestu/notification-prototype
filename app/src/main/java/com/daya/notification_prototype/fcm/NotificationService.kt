package com.daya.notification_prototype.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import android.content.Context
import android.content.Intent
import android.os.Build
import com.bumptech.glide.Glide
import com.daya.notification_prototype.view.main.MainActivity
import com.daya.notification_prototype.R

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.i(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.i(remoteMessage.toString())
        handleNotification(remoteMessage)

    }

    private fun handleNotification(remoteMessage: RemoteMessage) {
        val promotionalId = resources.getString(R.string.channel_id_promotional)
        val importanceId = resources.getString(R.string.channel_id_importance)
        var channelDesc = ""
        var channelName = ""
        val isChannelPromotional = remoteMessage.data["key_channel_id"] == promotionalId
        var channelId = ""
        if (isChannelPromotional) {
            channelId = promotionalId
            channelName = resources.getString(R.string.channel_name_promotional)
            channelDesc = resources.getString(R.string.channel_desc_promotional)
        } else {
            channelId = importanceId
            channelName = resources.getString(R.string.channel_name_importance)
            channelDesc = resources.getString(R.string.channel_desc_importance)
        }


        //TODO make pending intent to detail and visit link
        val pendingIntentViewDetail = Intent(this, MainActivity::class.java).let{
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            PendingIntent.getActivity(this,1, it ,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setSmallIcon(R.drawable.kalapa)
                .setAutoCancel(true)
                .setGroup(GROUP_KEY_NOTIFICATION)
                .addAction(R.drawable.ic_baseline_remove_red_eye_24, "detail", pendingIntentViewDetail)
                //.addAction(R.drawable.ic_baseline_link_24,"visit link",pendingIntentViewDetail)
                .setPriority(NotificationCompat.PRIORITY_HIGH)//support for API level < 24


        if (remoteMessage.notification?.imageUrl != null && remoteMessage.notification?.imageUrl.toString().isNotEmpty()) {
            notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(
                            Glide.with(this)
                                    .asBitmap()
                                    .load(remoteMessage.notification?.imageUrl)
                                    .submit()
                                    .get()
                    )
            )
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,channelId,NotificationManager.IMPORTANCE_HIGH).apply {
                name = channelName
                description = channelDesc
            }

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

    companion object{
        private const val GROUP_KEY_NOTIFICATION = "group_key_notification"
    }

}