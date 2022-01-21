package com.ssr_android.repository

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserRemoteRepository: UserRepository {
    override fun postFile(file : ImgFile ) : Single<Boolean> {
        return HttpClient.client
            .create(MainService::class.java)
            .postFile(file)
            .subscribeOn(Schedulers.io())
            .map {
                it.success
            }
            .onErrorReturn {
                    error ->
                Log.d("error", error.toString())
                false
            }
    }

    override fun postTotal(farmIssue: FarmIssue): Single<Boolean> {
        return HttpClient.client
            .create(MainService::class.java)
            .postTotal(farmIssue)
            .subscribeOn(Schedulers.io())
            .map {
                it.success
            }
            .onErrorReturn { error ->
                Log.d("error", error.toString())
                false
            }
    }
    companion object {
        fun getInstance(): UserRemoteRepository {
            return LazyHolder.INSTANCE
        }
    }
    private object LazyHolder {
        val INSTANCE = UserRemoteRepository()
    }

}