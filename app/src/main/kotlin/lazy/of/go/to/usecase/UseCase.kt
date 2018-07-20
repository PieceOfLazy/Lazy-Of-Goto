package lazy.of.go.to.usecase

import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
abstract class UseCase<Q, P> constructor(private val request: Q) {

    private var useCaseCallback: UseCaseCallback<P>? = null

    private var getLoadingFeature: (() -> LoadingFeature?)? = null

    fun setUseCaseCallback(useCaseCallback: UseCaseCallback<P>?) {
        this.useCaseCallback = useCaseCallback
    }

    fun setLoadingFeature(f: (() -> LoadingFeature?)?) {
        getLoadingFeature = f
    }

    protected fun getLoadingFeature(): LoadingFeature? = getLoadingFeature?.invoke()

    protected fun success(response: P) {
        useCaseCallback?.onSuccess(response)
    }

    protected fun error(throwable: Throwable) {
        useCaseCallback?.onError(throwable as? AppException ?: AppException(AppExceptionCode.UNKNOWN))
    }

    fun run() {
        executeUseCase(request)
    }

    protected abstract fun executeUseCase(request: Q)

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)
        fun onError(exception: AppException)
    }

    interface UseCaseLoadingFeatureCallback {
        fun getLoadingFeature(): LoadingFeature?
    }
}