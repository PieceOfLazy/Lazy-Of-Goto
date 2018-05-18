package lazy.of.go.to.common

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

class WeakRefHandler constructor(handleMessage: HandleMessage): Handler() {

    interface HandleMessage {
        fun onHandleMessage(msg: Message?)
    }

    private val delegate = WeakReference<HandleMessage>(handleMessage)

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)

        val handleMessage = delegate.get()
        handleMessage?.onHandleMessage(msg)
    }
}