package com.example.glmusic.data.repositoryImpl

import com.example.glmusic.data.model.GetTracksResponse
import com.example.glmusic.data.remote.RemoteDataProvider
import com.example.glmusic.domain.repository.TrackRepository
import retrofit2.Response
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val remoteDataProvider: RemoteDataProvider
) : TrackRepository {

    override suspend fun getTracks(): Response<GetTracksResponse> =
        remoteDataProvider.getTracks()
}