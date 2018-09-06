package lazy.of.go.to.usecase

import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

class UseCaseTransaction {

    private var callback: Callback? = null

    private var getLoadingFeature: (() -> LoadingFeature?)? = null

    private val useCases: MutableList<UseCase<*, *>> = mutableListOf()
    private var useCaseRunningCount = 0

    fun setUseCaseCallback(callback: Callback?) {
        this.callback = callback
    }

    fun setLoadingFeature(f: (() -> LoadingFeature?)?) {
        getLoadingFeature = f
    }

    private fun getLoadingFeature(): LoadingFeature? = getLoadingFeature?.invoke()

    private fun success() {
        useCaseRunningCount++
        if(useCaseRunningCount >= useCases.size) {
            for(useCase in useCases) {
                useCase.removeUseCaseCallback()
            }
            callback?.onSuccess()
            getLoadingFeature()?.loadingEnd()
        }
    }

    private fun error(exception: AppException) {
        for(useCase in useCases) {
            useCase.removeUseCaseCallback()
        }
        callback?.onError(exception)
        getLoadingFeature()?.loadingEnd()
    }

    fun addUseCase(useCase: UseCase<*, *>) {
        useCase.setTransactionCallBack(object : Callback {
            override fun onSuccess() {
                success()
            }

            override fun onError(exception: AppException) {
                error(exception)
            }
        })
        useCases.add(useCase)
    }

    fun run() {
        useCaseRunningCount = 0
        if(!useCases.isEmpty()) {
            getLoadingFeature()?.loadingStart()
            for(useCase in useCases) {
                useCase.run()
            }
        }
    }

    interface Callback {
        fun onSuccess()
        fun onError(exception: AppException)
    }
}