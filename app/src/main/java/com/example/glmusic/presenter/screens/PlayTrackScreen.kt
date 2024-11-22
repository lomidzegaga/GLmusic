package com.example.glmusic.presenter.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glmusic.R
import com.example.glmusic.presenter.model.TrackUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayTrackScreen(
    track: TrackUI,
    modifier: Modifier = Modifier,
) {
    val brush = remember {
        Brush.horizontalGradient(listOf(Color(0xFFFE8722), Color.Red))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()
            .background(Color(0xFF272725))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "Now Playing",
                style = TextStyle(
                    brush = brush,
                    fontSize = 25.sp
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )

        Text(
            text = track.name,
            color = Color.White,
            fontSize = 35.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "1:25", color = Color.White)
            Slider(
                state = SliderState(value = 0.5f, valueRange = 0f..1f),
                Modifier.padding(horizontal = 15.dp)
            )
            Text(text = track.audioDuration, color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        val icon by animateIntAsState(
            targetValue = if (true) R.drawable.pause else R.drawable.play,
            label = "icon"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = R.drawable.forward),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .rotate(180f)
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFFEFEFE))
                    .padding(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "play_arrow"
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.forward),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Preview
@Composable
private fun PlayTrackScreenPrev() {
    PlayTrackScreen(track = track)
}