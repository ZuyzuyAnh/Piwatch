package com.example.piwatch.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.piwatch.MainActivity
import com.example.piwatch.R

@RequiresApi(Build.VERSION_CODES.O)
fun makeNotification(message: String, context: Context) {
    val name = "Movies update Notification"
    val description = "Show notifications whenever work starts"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val id = "MOVIES_NOTIFICATION"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(id, name, importance)
        channel.description = description

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val builder = NotificationCompat.Builder(context, id)
        .setSmallIcon(R.drawable.logo_icon)
        .setContentTitle("New movies are updated")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
    }

    NotificationManagerCompat.from(context).notify(1, builder.build())
}