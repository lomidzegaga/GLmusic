package com.example.glmusic.data.model

import com.squareup.moshi.Json

data class GetTracksResponse(
    val headers: Headers,
    val results: List<Results>
)

data class Headers(
    val status: String,
    val code: Long,
    @Json(name = "error_message") val errorMessage: String,
    val warnings: String,
    @Json(name = "results_count") val resultsCount: Long
)

data class Results(
    val id: String,
    val name: String,
    val website: String,
    @Json(name = "joindate") val joinDate: String,
    val image: String,
    val tracks: List<Track>
)

data class Track(
    @Json(name = "album_id") val albumId: String,
    @Json(name = "album_name") val albumName: String,
    val id: String,
    val name: String,
    val duration: String,
    @Json(name = "releasedate") val releaseDate: String,
    @Json(name = "license_ccurl") val licenseCCurl: String,
    @Json(name = "album_image") val albumImage: String,
    val image: String,
    val audio: String,
    @Json(name = "audiodownload") val audioDownload: String,
    @Json(name = "audiodownload_allowed") val audioDownloadAllowed: Boolean
)