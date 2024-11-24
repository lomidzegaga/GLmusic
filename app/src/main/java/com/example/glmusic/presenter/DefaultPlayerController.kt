package com.example.glmusic.presenter

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player

class DefaultPlayerController(
    private val player: Player
) : PlayerController {
    override fun prepare() = player.prepare()

    override fun play(trackUri: Uri, startPosition: Long) {
        player.apply {
            setMediaItem(MediaItem.fromUri(trackUri))
            seekTo(startPosition)
            play()
        }
    }

    override fun pause() = player.pause()

    override fun seekTo(position: Long) = player.seekTo(position)

    override fun playNext() {
        TODO("Not yet implemented")
    }

    override fun playPrevious() {
        TODO("Not yet implemented")
    }

    override fun release() = player.release()

    override val currentPosition: Long
        get() = player.currentPosition

}