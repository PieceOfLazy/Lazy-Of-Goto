package lazy.of.go.to.base.feature

import android.support.annotation.StringRes
import android.widget.Toast

interface ToastFeature {

    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT)

    fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT)
}