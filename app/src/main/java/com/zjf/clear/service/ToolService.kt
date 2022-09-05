package com.zjf.clear.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.ui.activity.MainActivity

/**
 * create by colin
 * 2022/9/1
 */
class ToolService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val remoteView = RemoteViews(packageName, R.layout.notifycation_tools)
        remoteView.setOnClickPendingIntent(
            R.id.notification_clean,
            getGoToolsIntent(Constant.ID_CLEAN)
        )
        remoteView.setOnClickPendingIntent(
            R.id.notification_booster,
            getGoToolsIntent(Constant.ID_BOOSTER)
        )
        remoteView.setOnClickPendingIntent(
            R.id.notification_battery,
            getGoToolsIntent(Constant.ID_BATTERY)
        )
        remoteView.setOnClickPendingIntent(
            R.id.notification_cpu,
            getGoToolsIntent(Constant.ID_CPU)
        )



        val id = "${packageName}_tools"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel =
                NotificationChannel(id, "tools", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.setShowBadge(true)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, id)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.icon_app_logo)
            .setCustomContentView(remoteView)
            .setCustomBigContentView(remoteView)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun getGoToolsIntent(funcId: Int): PendingIntent {
        val goMainIntent = Intent(this, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        goMainIntent.putExtra("funcId", funcId)
        return PendingIntent
            .getActivity(this, funcId, goMainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}