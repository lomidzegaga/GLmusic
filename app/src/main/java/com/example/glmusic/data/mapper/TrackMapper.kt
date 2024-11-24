package com.example.glmusic.data.mapper

import android.os.Build
import com.example.glmusic.data.model.GetTracksResponse
import com.example.glmusic.presenter.model.DisplayableDate
import com.example.glmusic.presenter.model.TrackUI
import java.time.format.DateTimeFormatter
import java.util.Locale

fun GetTracksResponse.toTrackUI(): List<TrackUI> {
    return results.flatMap { result ->
        result.tracks.map { track ->
            TrackUI(
                id = track.id,
                name = track.name,
                releaseDate = track.releaseDate.toDisplayableDate(),
                image = track.image,
                audio = track.audio,
                audioDuration = track.duration.toFormattedDuration()
            )
        }
    }
}

fun String.toDisplayableDate(): DisplayableDate {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formattedDate = try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            val date = java.time.LocalDate.parse(this, inputFormatter)
            date.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()

            return DisplayableDate(
                value = "ERROR",
                formatted = "ERROR"
            )
        }
        return DisplayableDate(
            value = this,
            formatted = formattedDate
        )
    }
    return DisplayableDate(value = "ERROR", formatted = "ERROR")
}

fun String.toFormattedDuration(): String {
    val durationInSeconds = this.toIntOrNull() ?: return "Invalid"
    val minutes = durationInSeconds / 60
    val seconds = durationInSeconds % 60
    return String.format(Locale.US, "%2d:%02d", minutes, seconds)
}