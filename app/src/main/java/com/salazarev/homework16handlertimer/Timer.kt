import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.lang.ref.WeakReference

class Timer(textView: WeakReference<TextView>, private val startTime: Int) {
    var currentTime: Int = startTime
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable

    init {
        textView.get()?.text = currentTime.toString()
        runnable = object : Runnable {
            override fun run() {
                if (currentTime >= 0) {
                    textView.get()?.text = (currentTime).toString()
                    if (currentTime > 0) --currentTime
                    handler.postDelayed(this, 1000)
                } else handler.removeCallbacks(this)
            }
        }
    }

    constructor(textView: WeakReference<TextView>, startTime: Int, buffTime: Int) : this(
        textView,
        startTime
    ) {
        currentTime = buffTime
        if (buffTime != startTime) start(buffTime)
        textView.get()?.text = buffTime.toString()
    }

    fun start(time: Int = startTime) {
        currentTime = time
        handler.removeCallbacks(runnable)
        handler.post(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}