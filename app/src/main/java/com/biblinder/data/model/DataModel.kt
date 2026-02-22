data class SyncAccount(
    val provider: SyncProvider,
    val accessToken: String,
    val refreshToken: String?,
    val username: String
)

enum class SyncProvider { MyAnimeList, AniList }
