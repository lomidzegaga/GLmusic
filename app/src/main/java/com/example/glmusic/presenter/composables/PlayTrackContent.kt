package com.example.glmusic.presenter.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glmusic.R
import com.example.glmusic.presenter.model.TrackUI
import com.example.glmusic.presenter.screens.track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayTrackContent(
    track: TrackUI,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "1:25", color = Color.White)
            Slider(
                state = SliderState(value = 0.5f, valueRange = 0f..1f),
                Modifier.fillMaxWidth(0.8f)
            )
            Text(text = track.audioDuration, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun PlayTrackContentPrev() {
    PlayTrackContent(track)
}