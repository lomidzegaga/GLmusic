package com.example.glmusic.presenter.model

data class TrackUI(
    val id: String,
    val name: String,
    val releaseDate: DisplayableDate,
    val audio: String,
    val audioDuration: String
)

data class DisplayableDate(
    val value: String,
    val formatted: String
)