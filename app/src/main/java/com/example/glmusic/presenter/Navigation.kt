package com.example.glmusic.presenter

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.glmusic.data.mapper.toDisplayableDate
import com.example.glmusic.data.mapper.toFormattedDuration
import com.example.glmusic.presenter.model.TrackUI
import com.example.glmusic.presenter.screens.PlayTrackScreen
import com.example.glmusic.presenter.screens.TracksListScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        val viewModel = hiltViewModel<MainViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        NavHost(
            navController = navController,
            startDestination = Screens.TracksListScreen,
            modifier = modifier
        ) {
            composable<Screens.TracksListScreen> {
                TracksListScreen(
                    animatedVisibilityScope = this,
                    state = state,
                    onItemClick = { track ->
                        viewModel.selectTrack(track)
                        navController.navigate(Screens.PlayTrackScreen)
                    }
                )
            }

            composable<Screens.PlayTrackScreen> {
                PlayTrackScreen(
                    track = state.selectedTrack ?: TrackUI(
                        id = "ERROR",
                        name = "OOPS, SOMETHING STRANGE HAPPENED!",
                        releaseDate = "ERROR".toDisplayableDate(),
                        audio = "ERROR",
                        audioDuration = "ERROR".toFormattedDuration()
                    ),
                    animatedVisibilityScope = this
                )
            }
        }
    }
}

sealed interface Screens {

    @Serializable
    object TracksListScreen

    @Serializable
    object PlayTrackScreen

}