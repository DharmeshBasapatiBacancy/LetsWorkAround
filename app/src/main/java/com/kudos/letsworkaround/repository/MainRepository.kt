package com.kudos.letsworkaround.repository

import com.kudos.letsworkaround.network.models.DevBytesApiResponse
import com.kudos.letsworkaround.network.service.DevBytesApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val devBytesApiService: DevBytesApiService,
) {

    suspend fun getPlaylist(): DevBytesApiResponse {
        return devBytesApiService.getPlaylist()
    }

}