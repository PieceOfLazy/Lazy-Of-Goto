package lazy.of.go.to.base

import lazy.of.go.to.exception.AppException
import kotlin.reflect.KClass

/**
 * @author lazy.of.zpdl
 */
interface MvpView<in P> : lazy.of.framework.library.mvp.MvpView<P> {
    fun <T: Any> getFeature(type: KClass<T>): T?

    fun onException(exception: AppException)
}