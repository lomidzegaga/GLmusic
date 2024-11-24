package com.example.glmusic.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glmusic.data.mapper.toTrackUI
import com.example.glmusic.domain.repository.TrackRepository
import com.example.glmusic.presenter.model.TrackUI
import com.example.glmusic.presenter.screens.OnPlayerControlsClick
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
    private val playerController: PlayerController
) : ViewModel() {

    init {
        playerController.prepare()
    }

    private val _state = MutableStateFlow(TrackState())
    val state = _state.onStart { getTracks() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrackState()
        )

    var lastTrack: TrackUI? = null
    private var trackLastPosition by mutableLongStateOf(0L)
    var isMusicPlaying by mutableStateOf(false)

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
        lastTrack = track
    }

    fun onPlayerControlsClick(userInput: OnPlayerControlsClick) {
        when (userInput) {
            OnPlayerControlsClick.Next -> playerController.playNext()
            OnPlayerControlsClick.PlayPause -> togglePlayPause()
            OnPlayerControlsClick.Previous -> playerController.playPrevious()
        }
    }

    private fun togglePlayPause() {
        isMusicPlaying = isMusicPlaying.not()

        if (isMusicPlaying) {
            playMusic()
        } else {
            pauseMusic()
        }
    }

    fun playMusic() {
        _state.value.selectedTrack?.let { track ->
            val isSameTrack = lastTrack == track

            if (isSameTrack) {
                playerController.seekTo(trackLastPosition)
            } else {
                playerController.play(track.audio.toUri(), trackLastPosition)
            }
        }
    }

    fun pauseMusic() {
        trackLastPosition = playerController.currentPosition
        playerController.pause()
    }

    private fun playNext() = playerController.playNext()
    private fun playPrevious() = playerController.playPrevious()


    override fun onCleared() {
        super.onCleared()
        playerController.release()
    }
}


@Immutable
data class TrackState(
    val isDataLoading: Boolean = false,
    val tracks: List<TrackUI> = emptyList(),
    val selectedTrack: TrackUI? = null
)