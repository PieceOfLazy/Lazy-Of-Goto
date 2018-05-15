package lazy.of.go.to.base

import android.support.annotation.StringRes
import android.widget.Toast
import lazy.of.framework.library.mvp.MvpContractListener

/**
 * @author lazy.of.zpdl
 */
interface BaseListener: MvpContractListener {

    fun onLoadingStart()

    fun onLoadingEnd()

    fun onShowToast(msg: String, duration: Int = Toast.LENGTH_SHORT)

    fun onShowToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT)
}