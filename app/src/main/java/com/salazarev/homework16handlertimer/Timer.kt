package com.salazarev.homework16handlertimer

import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class Timer(
    private val textView: WeakReference<TextView>,
    var currentTime: Int,
    private val allTime: Int = currentTime
) {
    private var disposable: Disposable? = null

    init {
        textView.get()?.text = currentTime.toString()
        if (currentTime != allTime && currentTime>=0) start(currentTime)
    }

    fun start(time: Int = allTime) {
        stop()
        currentTime = time + 1
        getObservable().subscribe(getObserver())
    }

    private fun getObservable(): Observable<Int> {

        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .take(currentTime.toLong())
            .map { --currentTime }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(value: Int) {
                textView.get()?.text = value.toString()
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
    }

    fun stop() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}