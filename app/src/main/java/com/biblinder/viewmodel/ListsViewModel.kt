package com.biblinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblinder.data.local.AnimeDao
import com.biblinder.data.local.AnimeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val dao: AnimeDao
) : ViewModel() {

    private val _lists = MutableStateFlow<List<AnimeEntity>>(emptyList())
    val lists: StateFlow<List<AnimeEntity>> = _lists.asStateFlow()

    init {
        refreshLists()
    }

    private fun refreshLists() {
        viewModelScope.launch(Dispatchers.IO) {
            _lists.value = dao.getAll()
        }
    }

    fun moveAnime(anime: AnimeEntity, targetList: String = "PLAN_TO_WATCH") {
        viewModelScope.launch(Dispatchers.IO) {
            val moved = anime.copy(listType = targetList)
            dao.insert(moved)
            refreshLists()
        }
    }

    fun deleteAnime(anime: AnimeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(anime.id)
            refreshLists()
        }
    }
}
