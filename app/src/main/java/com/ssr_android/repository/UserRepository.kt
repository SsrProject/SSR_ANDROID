package com.ssr_android.repository

import io.reactivex.Single

interface UserRepository {
    fun postTotal(farmIssue: FarmIssue): Single<Boolean>
}