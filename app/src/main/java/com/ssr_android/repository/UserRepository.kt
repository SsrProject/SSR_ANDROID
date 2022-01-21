package com.ssr_android.repository

import io.reactivex.Single
import java.io.File

interface UserRepository {
    fun postTotal(farmIssue: FarmIssue): Single<Boolean>
    fun postFile(file: ImgFile): Single<Boolean>
}