package com.example.glmusic.domain.repository

import com.example.glmusic.data.model.GetTracksResponse
import retrofit2.Response

fun interface TrackRepository {

    suspend fun getTracks(): Response<GetTracksResponse>
}