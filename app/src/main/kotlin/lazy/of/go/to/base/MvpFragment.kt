package lazy.of.go.to.base

import android.content.Context
import dagger.android.support.DaggerFragment
import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView
import lazy.of.go.to.base.feature.FeatureListener
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.di.ActivityScoped

/**
 * @author piece.of.lazy
 */
@ActivityScoped
abstract class MvpFragment<V : MvpView<P>, P : MvpPresenter<V>> : DaggerFragment(), MvpView<P>, LoadingFeature {

    private var _presenter: P? = null

    protected var featureListener: FeatureListener? = null

    override fun setPresenter(presenter: P?) {
        _presenter = presenter
    }

    protected fun getPresenter(): P? = _presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is FeatureListener) {
            featureListener = context
        }
        _presenter?.onViewAttach(onBindPresenterView())
    }

    override fun onDetach() {
        super.onDetach()

        _presenter?.onViewDetach()
        featureListener = null
    }

    override fun loadingStart() {
        featureListener?.onGetFeature(LoadingFeature::class)?.loadingStart()
    }

    override fun loadingEnd() {
        featureListener?.onGetFeature(LoadingFeature::class)?.loadingEnd()
    }

    abstract fun onBindPresenterView(): V
}