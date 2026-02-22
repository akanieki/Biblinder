class SeasonWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val repo = getSeasonRepo()
        val shows = repo.getCurrentSeason()
        shows.filter { it.isWatching }.forEach {
            scheduleNotification(it)
        }
        return Result.success()
    }

    private fun scheduleNotification(anime: Anime) {
        val trigger = calculateNextEpisodeTime(anime.broadcast)
        NotificationManagerCompat.from(applicationContext).notify(
            anime.id,
            NotificationCompat.Builder(applicationContext, "biblinder_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("${anime.title} airs Soon!")
                .setContentText("New episode today (${anime.broadcast.day})")
                .setColor(Color(0xFFA3E635))
                .build()
        )
    }
}
