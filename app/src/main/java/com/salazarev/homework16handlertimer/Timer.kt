import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class Timer(val textView: WeakReference<TextView>, var currentTime: Int, private val allTime: Int = currentTime) {
    val observable: Observable<Int>
    var disposable: Disposable? = null

    init {
        textView.get()?.text = currentTime.toString()
        observable = Observable.interval(0,1, TimeUnit.SECONDS)
            .take(currentTime.toLong()+1)
            .map { --currentTime }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
        if (currentTime!=allTime) start(currentTime)
    }

    fun start(time: Int = allTime) {
        stop()
        currentTime = time+1
        disposable = observable.subscribe { textView.get()?.text = it.toString() }
    }

    fun stop() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}