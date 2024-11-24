package com.example.glmusic.presenter

import android.net.Uri

interface PlayerController {
    fun prepare()
    fun play(trackUri: Uri, startPosition: Long = 0L)
    fun pause()
    fun seekTo(position: Long)
    fun playNext()
    fun playPrevious()
    fun release()
    val currentPosition: Long
}