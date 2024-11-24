package com.example.glmusic.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.glmusic.presenter.screens.PlayTrackScreen
import com.example.glmusic.presenter.screens.TracksListScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screens.TracksListScreen,
        modifier = modifier
    ) {
        composable<Screens.TracksListScreen> {
            TracksListScreen(
                state = state, onItemClick = { track ->
                    viewModel.selectTrack(track)
                    navController.navigate(Screens.PlayTrackScreen)
                }
            )
        }

        composable<Screens.PlayTrackScreen> {
            state.selectedTrack?.let { track ->
                PlayTrackScreen(
                    track = track,
                    controlsClick = viewModel::onPlayerControlsClick,
                    isMusicPlaying = viewModel.isMusicPlaying
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