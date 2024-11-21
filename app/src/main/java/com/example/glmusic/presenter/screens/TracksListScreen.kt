package com.example.glmusic.presenter.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glmusic.presenter.TrackState
import com.example.glmusic.presenter.model.TrackUI

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TracksListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: TrackState,
    onItemClick: (TrackUI) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()
    ) {
        Text(
            text = "Explore Tracks here",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(items = state.tracks, key = { track -> track.id }) { track ->
                TrackItem(
                    onItemClick = onItemClick,
                    track = track,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TrackItem(
    onItemClick: (TrackUI) -> Unit,
    track: TrackUI,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(20.dp))
        .clickable { onItemClick.invoke(track) }
        .width(150.dp)
        .height(250.dp)
        .background(Color(0xFF9AB0FC))
    ) {

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = track.name,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = track.audioDuration.plus(" min"), fontSize = 12.sp
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        modifier = Modifier
                            .size(10.dp)
                            .sharedElement(state = rememberSharedContentState(key = track.id),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }),
                        contentDescription = "play_arrow"
                    )
                }
            }
        }
    }
}