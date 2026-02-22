package com.biblinder

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biblinder.data.model.Anime

class SeasonWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        // TODO: getSeasonRepo() bağımlılık enjeksiyonu ile sağlanacak
        // Şimdilik stub olarak bırakıldı
        return Result.success()
    }

    private fun scheduleNotification(anime: Anime) {
        if (!hasNotificationPermission()) return

        val notification = NotificationCompat.Builder(applicationContext, "biblinder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("${anime.title} airs Soon!")
            .setContentText("New episode today")
            .setColor(0xFFA3E635.toInt())
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(anime.id, notification)
    }

    private fun hasNotificationPermission(): Boolean {
        return NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()
    }
}
