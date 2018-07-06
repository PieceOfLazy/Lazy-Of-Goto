package lazy.of.go.to.base

import android.content.Context
import android.content.res.Configuration
import dagger.android.DaggerFragment
import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView
import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

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

    protected var featureListener: GetFeature? = null

    override fun setPresenter(presenter: P?) {
        _presenter = presenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is GetFeature) {
            featureListener = context
        }
        _presenter?.onViewAttach(onBindPresenterView())
    }

    override fun onDetach() {
        super.onDetach()

        _presenter?.onViewDetach()
        featureListener = null
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun loadingStart() {
        featureListener?.getFeature(LoadingFeature::class)?.loadingStart()
    }

    override fun loadingEnd() {
        featureListener?.getFeature(LoadingFeature::class)?.loadingEnd()
    }

    abstract fun onBindPresenterView(): V
}