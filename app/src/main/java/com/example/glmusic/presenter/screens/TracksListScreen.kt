package com.example.glmusic.presenter.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.glmusic.R
import com.example.glmusic.data.mapper.toDisplayableDate
import com.example.glmusic.data.mapper.toFormattedDuration
import com.example.glmusic.presenter.TrackState
import com.example.glmusic.presenter.model.TrackUI

@Composable
fun TracksListScreen(
    state: TrackState,
    onItemClick: (TrackUI) -> Unit,
    modifier: Modifier = Modifier
) {

    val brush = remember {
        Brush.horizontalGradient(listOf(Color(0xFFFE8722), Color.Red))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF2B2C2F))
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) {
        Text(
            text = "Explore Tracks here",
            modifier = Modifier.padding(20.dp),
            style = TextStyle(
                brush = brush,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (state.tracks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = state.tracks, key = { track -> track.id }) { track ->
                    TrackItem(
                        onItemClick = onItemClick,
                        track = track,
                    )
                }
            }
        }

    }
}

@Composable
fun TrackItem(
    onItemClick: (TrackUI) -> Unit,
    track: TrackUI,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(track) }
            .background(color = Color(0xFF2B2C2F)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {
            if (LocalInspectionMode.current) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = track.image,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = track.name,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = track.audioDuration,
                    fontSize = 14.sp,
                    color = Color(0xFFFE8722)
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFFFE8722))
                .padding(11.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.play),
                modifier = Modifier
                    .size(10.dp),
                contentDescription = "play_arrow"
            )
        }
    }
}


@Preview
@Composable
private fun TracksListScreenPrev() {
    TracksListScreen(
        onItemClick = { },
        state = state
    )
}

@Preview
@Composable
private fun TrackItemPrev() {
    TrackItem(
        onItemClick = { },
        track = track
    )
}

internal val track = TrackUI(
    id = "2",
    name = "eminem",
    releaseDate = "".toDisplayableDate(),
    image = "",
    audio = "",
    audioDuration = "325".toFormattedDuration()
)

internal val state = TrackState(
    tracks = listOf(track)
)
