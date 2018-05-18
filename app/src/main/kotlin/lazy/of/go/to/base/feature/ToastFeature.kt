package lazy.of.go.to.base.feature

import android.support.annotation.StringRes

interface ToastFeature {

    fun showToast(message: CharSequence, duration: Int)

    fun showToast(@StringRes message: Int, duration: Int)
}