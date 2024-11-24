package com.example.glmusic.presenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glmusic.presenter.composables.BottomBar
import com.example.glmusic.presenter.composables.PlayTrackContent
import com.example.glmusic.presenter.composables.TopBar
import com.example.glmusic.presenter.model.TrackUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayTrackScreen(
    track: TrackUI,
    isMusicPlaying: Boolean,
    controlsClick: (OnPlayerControlsClick) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF272725))
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {

        TopBar { }

        PlayTrackContent(track)

        Spacer(modifier = Modifier.height(30.dp))

        BottomBar(
            isPlaying = isMusicPlaying, controlsClick = controlsClick
        )
    }
}

sealed interface OnPlayerControlsClick {
    object PlayPause : OnPlayerControlsClick
    object Next : OnPlayerControlsClick
    object Previous : OnPlayerControlsClick
}


@Preview
@Composable
private fun PlayTrackScreenPrev() {
    PlayTrackScreen(track = track, isMusicPlaying = false, { })
}