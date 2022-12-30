package com.kudos.letsworkaround.network.service

import com.kudos.letsworkaround.network.models.DevBytesApiResponse
import retrofit2.http.GET

interface DevBytesApiService {

    @GET("devbytes")
    suspend fun getPlaylist(): DevBytesApiResponse

}