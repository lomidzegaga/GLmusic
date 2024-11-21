package com.example.glmusic.presenter

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.glmusic.data.mapper.toTrackUI
import com.example.glmusic.domain.repository.TrackRepository
import com.example.glmusic.presenter.model.TrackUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TrackRepository,
    private val savedStateHandle: SavedStateHandle,
    private val player: Player
) : ViewModel() {

    init {
        player.prepare()
    }

    private val _state = MutableStateFlow(TrackState())
    val state = _state.onStart { getTracks() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrackState()
        )

    private var lastTrack by mutableStateOf(TrackState().selectedTrack)

    private fun getTracks() {
        _state.update { it.copy(isDataLoading = true) }
        viewModelScope.launch {
            val response = repository.getTracks()

            if (response.isSuccessful) {
                _state.update {
                    it.copy(
                        isDataLoading = false,
                        tracks = response.body()?.toTrackUI() ?: emptyList()
                    )
                }
            }
        }
    }

    fun selectTrack(track: TrackUI) {
        _state.update { it.copy(selectedTrack = track) }
    }

    fun playMusic() {
        val isSameTrackPlaying = lastTrack == _state.value.selectedTrack

        if (isSameTrackPlaying) {
            val lastPosition = _state.value.lastPosition
            player.seekTo(lastPosition)
        } else {
            _state.update { it.copy(lastPosition = 0L) }
            player.setMediaItem(
                MediaItem.fromUri(
                    _state.value.selectedTrack?.audio?.toUri() ?: Uri.EMPTY
                )
            )
        }

        player.play()
    }

    fun pauseMusic() {
        val currentTrackPosition = player.currentPosition
        _state.update { it.copy(lastPosition = currentTrackPosition) }
        lastTrack = _state.value.selectedTrack
        player.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}


@Immutable
data class TrackState(
    val isDataLoading: Boolean = false,
    val tracks: List<TrackUI> = emptyList(),
    val selectedTrack: TrackUI? = null,
    val lastPosition: Long = 0L
)