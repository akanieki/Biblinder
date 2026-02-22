class SyncRepository(
    private val dao: AnimeDao,
    private val malService: MalApiService,
    private val aniListService: AniListService
) {
    suspend fun importFromRemote(provider: SyncProvider, token: String) {
        val list = when (provider) {
            SyncProvider.MyAnimeList -> malService.getUserList(token)
            SyncProvider.AniList -> aniListService.getUserList(token)
        }
        dao.insertAll(list.map { it.toEntity() })
    }

    suspend fun pushUpdate(anime: AnimeEntity, provider: SyncProvider, token: String) {
        withContext(Dispatchers.IO) {
            when (provider) {
                SyncProvider.MyAnimeList -> malService.updateAnimeStatus(token, anime.id, anime.listType)
                SyncProvider.AniList -> aniListService.updateAnimeStatus(token, anime.id, anime.listType)
            }
        }
    }
}
