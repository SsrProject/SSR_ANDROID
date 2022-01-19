package com.ssr_android.network

import com.ssr_android.network.response.DefaultResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainService {

    @POST(baseUrl)
    fun postTotal(
        @Body farmIssue: FarmIssue
    ): Single<DefaultResponse>

    @POST(baseUrl)
    fun postImg(

    )
    @GET(baseUrl)
    fun getUrl(

    )
}