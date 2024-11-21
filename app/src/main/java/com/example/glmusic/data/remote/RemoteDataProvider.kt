package com.example.glmusic.data.remote

import com.example.glmusic.data.model.GetTracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataProvider {

    @GET("v3.0/artists/tracks")
    suspend fun getTracks(
        @Query("format") format: String = "jsonpretty",
        @Query("order") order: String = "track_name_desc",
        @Query("name") name: String = "we are fm",
        @Query("album_datebetween") albumDateBetween: String = "0000-00-00_2024-11-11"
    ): Response<GetTracksResponse>

}