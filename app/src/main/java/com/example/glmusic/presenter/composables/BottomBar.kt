package com.example.glmusic.presenter.composables

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glmusic.R
import com.example.glmusic.presenter.screens.OnPlayerControlsClick
import com.example.glmusic.presenter.utils.clickWithoutRipple

@Composable
fun BottomBar(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    controlsClick: (OnPlayerControlsClick) -> Unit
) {

    val icon by animateIntAsState(
        targetValue = if (isPlaying) R.drawable.pause else R.drawable.play,
        label = "icon"
    )

    Row(
        modifier = modifier
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
                .clickWithoutRipple { controlsClick(OnPlayerControlsClick.Previous) }
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFFFEFEFE))
                .padding(20.dp)
                .clickWithoutRipple { controlsClick(OnPlayerControlsClick.PlayPause) }
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
            modifier = Modifier
                .size(50.dp)
                .clickWithoutRipple { controlsClick(OnPlayerControlsClick.Next) }
        )
    }
}

@Preview
@Composable
private fun BottomBarPrev() {
    BottomBar(true) { }
}