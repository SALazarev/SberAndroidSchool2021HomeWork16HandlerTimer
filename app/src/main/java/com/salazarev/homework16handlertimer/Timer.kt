import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.lang.ref.WeakReference

class Timer(tv: WeakReference<TextView>, private val startTime: Int) {
    var currentTime: Int = startTime
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable
    private val textView: TextView = tv.get()!!

    init {
        textView.text = currentTime.toString()
        runnable = object : Runnable {
            override fun run() {
                if (currentTime >= 0) {
                    textView.text = (currentTime).toString()
                    if (currentTime > 0) --currentTime
                    handler.postDelayed(this, 1000)
                } else handler.removeCallbacks(this)
            }
        }
    }

    constructor(tv: WeakReference<TextView>, startTime: Int, buffTime: Int) : this(tv, startTime) {
        if (buffTime != startTime) start(buffTime)
        textView.text = buffTime.toString()
    }

    fun start(time: Int = startTime) {
        currentTime = time
        handler.removeCallbacks(runnable)
        handler.post(runnable)
    }
}