package com.ssr_android.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

class MainViewModel: ViewModel() {
    private val editNum = ObservableField("")
    private val editAddress = ObservableField("")

    private val userRepository = UserRemoteRepository.getInstance()
    private val disposables = CompositeDisposable()

    val Api = Retrofit.api

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    fun postTotal() {
        userRepository.postTotal(
            FarmIssue(
                editNum = editNum.get() ?: "",
                editAddress = editAddress.get() ?: "",
                imgUrl = ""
            )
        )
            .subscribe { result ->
                itemEventRelay.accept(AddSuccessEvent(result))
            }
            .addTo(disposables)
    }

    class AddSuccessEvent(val isSuccess: Boolean) : RxAction
}