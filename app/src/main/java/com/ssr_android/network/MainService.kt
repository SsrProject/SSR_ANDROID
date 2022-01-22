package com.ssr_android.network

import com.ssr_android.network.response.DefaultResponse
import io.reactivex.Single
import retrofit2.http.*
import java.io.File

interface MainService {

    @POST(baseUrl)
    fun postTotal(
        @Body farmIssue: FarmIssue
    ): Single<DefaultResponse>

    @Multipart
    @POST(baseUrl)
    fun postFile(
        @Part file : File
    ): Single<DefaultResponse>

    @GET(baseUrl)
    fun getUrl(
    )
}