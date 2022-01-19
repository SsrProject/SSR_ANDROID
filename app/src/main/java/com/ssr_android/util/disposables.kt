package com.ssr_android.util

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 * @author MyeongKi
 */
fun Disposable.addTo(
    disposableContainer: DisposableContainer
): Disposable = apply {
    disposableContainer.add(this)
}