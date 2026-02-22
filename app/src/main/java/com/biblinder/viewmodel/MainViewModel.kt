package com.biblinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblinder.data.api.JikanRepository
import com.biblinder.data.api.SyncRepository
import com.biblinder.data.local.AnimeDao
import com.biblinder.data.local.AnimeEntity
import com.biblinder.data.model.Anime
import com.biblinder.swipe.AnimeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jikanRepository: JikanRepository,
    private val dao: AnimeDao,
    private val syncRepository: SyncRepository
) : ViewModel() {

    private val _animePool = MutableStateFlow<List<Anime>>(emptyList())
    val animePool: StateFlow<List<Anime>> = _animePool.asStateFlow()

    private val _currentMood = MutableStateFlow<String?>(null)
    val currentMood: StateFlow<String?> = _currentMood.asStateFlow()

    var currentIndex = MutableStateFlow(0)

    fun loadInitialPool() {
        viewModelScope.launch {
            _animePool.value = jikanRepository.fetchMixed()
            currentIndex.value = 0
        }
    }

    fun selectMood(mood: String) {
        _currentMood.value = mood
        viewModelScope.launch {
            val moodBased = jikanRepository.fetchMoodBased(mood)
            _animePool.value = moodBased
            currentIndex.value = 0
        }
    }

    fun onSwipe(action: AnimeAction) {
        val anime = _animePool.value.getOrNull(currentIndex.value) ?: return

        val entity = AnimeEntity(
            id = anime.id,
            title = anime.title,
            synopsis = anime.synopsis,
            imageUrl = anime.imageUrl,
            malScore = anime.malScore ?: 0f,
            personalScore = null,
            listType = action.name
        )

        // Save locally + sync remotely
        viewModelScope.launch {
            dao.insert(entity)
            syncRepository.pushUpdate(entity, /* user-preferred provider */ com.biblinder.data.api.SyncProvider.AniList, /* token */ "Bearer ...")
        }

        // Move to next
        val next = currentIndex.value + 1
        if (next < _animePool.value.size) {
            currentIndex.value = next
        } else {
            // fetch more content when depleted
            viewModelScope.launch { loadInitialPool() }
        }
    }
}
