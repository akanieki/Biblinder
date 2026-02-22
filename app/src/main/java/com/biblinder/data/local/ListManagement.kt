package com.biblinder.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Liste Türleri
enum class ListType {
    WATCHED,
    PLAN_TO_WATCH,
    WATCHING,
    DISCARD
}

// Veritabanı Tablosu (Anime Listesi)
@Entity(tableName = "anime_list_items")
data class AnimeListItem(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String,
    val synopsis: String,
    val malScore: Double?,
    val personalRating: Float,
    val defaultListType: ListType?, // Can be null if only in custom list
    val customListId: Long?, // Can be null if only in default list
    val addedTimestamp: Long = System.currentTimeMillis()
)

// Veritabanı Tablosu (Özel Listeler)
@Entity(tableName = "custom_lists")
data class CustomList(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdTimestamp: Long = System.currentTimeMillis()
)

// Veritabanı Sorguları (DAO)
@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_list_items WHERE defaultListType = :listType")
    fun getAnimeByDefaultList(listType: ListType): Flow<List<AnimeListItem>>
    
    @Query("SELECT * FROM anime_list_items WHERE customListId = :customListId")
    fun getAnimeByCustomList(customListId: Long): Flow<List<AnimeListItem>>
    
    @Query("SELECT * FROM anime_list_items WHERE malId = :malId")
    suspend fun getAnimeById(malId: Int): AnimeListItem?
    
    @Query("SELECT malId FROM anime_list_items")
    suspend fun getAllSeenAnimeIds(): List<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeListItem)
    
    @Update
    suspend fun updateAnime(anime: AnimeListItem)
    
    @Delete
    suspend fun deleteAnime(anime: AnimeListItem)
    
    @Query("DELETE FROM anime_list_items WHERE malId = :malId")
    suspend fun deleteById(malId: Int)
    
    @Query("SELECT * FROM custom_lists ORDER BY createdTimestamp DESC")
    fun getAllCustomLists(): Flow<List<CustomList>>
    
    @Insert
    suspend fun insertCustomList(customList: CustomList): Long
    
    @Delete
    suspend fun deleteCustomList(customList: CustomList)
}

// Liste Yönetimi Deposu (Repository)
class ListManagementRepository(private val dao: AnimeDao) {
    
    suspend fun moveToDefaultList(anime: AnimeListItem, newListType: ListType) {
        val current = dao.getAnimeById(anime.malId)
        
        if (current != null) {
            // Zaten varsa sadece varsayılan listesini güncelle
            dao.updateAnime(current.copy(defaultListType = newListType))
        } else {
            // Yeni öğe ise ekle
            dao.insertAnime(anime.copy(defaultListType = newListType))
        }
    }
    
    suspend fun moveToCustomList(anime: AnimeListItem, customListId: Long) {
        val current = dao.getAnimeById(anime.malId)
        
        if (current != null) {
            dao.updateAnime(current.copy(customListId = customListId))
        } else {
            dao.insertAnime(anime.copy(customListId = customListId))
        }
    }
    
    suspend fun removeFromDefaultList(malId: Int) {
        val current = dao.getAnimeById(malId) ?: return
        
        if (current.customListId == null) {
            // Başka listede yoksa tamamen sil
            dao.deleteById(malId)
        } else {
            // Custom listede varsa sadece default listeyi null yap
            dao.updateAnime(current.copy(defaultListType = null))
        }
    }
    
    suspend fun removeFromCustomList(malId: Int) {
        val current = dao.getAnimeById(malId) ?: return
        
        if (current.defaultListType == null) {
            dao.deleteById(malId)
        } else {
            dao.updateAnime(current.copy(customListId = null))
        }
    }
    
    suspend fun deleteCompletely(malId: Int) {
        dao.deleteById(malId)
    }
}
