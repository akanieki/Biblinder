package com.biblinder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_table") // AnimeEntity içindeki tablo adın farklıysa burayı güncelle
    suspend fun getAll(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: AnimeEntity)

    @Query("DELETE FROM anime_table WHERE id = :animeId")
    suspend fun delete(animeId: Int)
}
