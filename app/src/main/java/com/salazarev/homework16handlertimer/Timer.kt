import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.lang.ref.WeakReference

class Timer(private val tv: WeakReference<TextView>, private val startTime: Int) {
    var currentTime: Int = startTime
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable

    init {
        tv.get()?.text = currentTime.toString()
        runnable = object : Runnable {
            override fun run() {
                if (currentTime >= 0) {
                    if (currentTime > 0) tv.get()?.text = (currentTime--).toString()
                    else if (currentTime == 0) tv.get()?.text = (currentTime).toString()
                    handler.postDelayed(this, 1000)
                } else handler.removeCallbacks(this)
            }
        }
    }

    constructor(tv: WeakReference<TextView>, startTime: Int, buffTime: Int) : this(tv, startTime) {
        start(buffTime)
        tv.get()?.text = buffTime.toString()
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