package lazy.of.go.to.base

import lazy.of.go.to.exception.AppException

/**
 * @author lazy.of.zpdl
 */
interface MvpView<in P> : lazy.of.framework.library.mvp.MvpView<P> {

    fun onException(exception: AppException)
}