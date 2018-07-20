package lazy.of.go.to.base

import android.content.Context
import android.content.res.Configuration
import dagger.android.DaggerFragment
import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.base.feature.ToastFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * @author piece.of.lazy
 */
@ActivityScoped
abstract class MvpFragment<V : MvpView<P>, P : MvpPresenter<V>> : DaggerFragment(), MvpView<P>, LoadingFeature {

    @Inject
    lateinit var log: Log

    protected var _presenter: P? = null
        private set(value) {
            field = value
        }

    protected var featureCallback: GetFeature? = null

    override fun setPresenter(presenter: P?) {
        _presenter = presenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is GetFeature) {
            featureCallback = context
        }
        _presenter?.onViewAttach(onBindPresenterView())
    }

    override fun onDetach() {
        super.onDetach()

        _presenter?.onViewDetach()
        featureCallback = null
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun loadingStart() {
        featureCallback?.getFeature(LoadingFeature::class)?.loadingStart()
    }

    override fun loadingEnd() {
        featureCallback?.getFeature(LoadingFeature::class)?.loadingEnd()
    }

    override fun <T : Any> getFeature(type: KClass<T>): T? = featureCallback?.getFeature(type)

    @Suppress("NON_EXHAUSTIVE_WHEN")
    override fun onException(exception: AppException) {
        when(exception.exceptionCode) {
            AppExceptionCode.UNKNOWN -> {
                featureCallback?.getFeature(ToastFeature::class)?.showToast("알수 없는 에러가 발생했습니다.\n잠시 후 다시 시도해 주세요.")
            }
            AppExceptionCode.DB -> {
//                exception.cause?.message
                featureCallback?.getFeature(ToastFeature::class)?.showToast("DB가 불안합니다.\n잠시 후 다시 시도해 주세요.")
            }
        }
    }

    abstract fun onBindPresenterView(): V
}