package lazy.of.go.to.base

import android.content.Context
import android.view.View
import lazy.of.framework.library.panel.PanelLC
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.exception.AppException
import kotlin.reflect.KClass

/**
 * @author lazy.of.zpdl
 */
abstract class MvpPanel<V : MvpView<P>, P : MvpPresenter<V>> constructor(private val mvpView: MvpView<*>, protected val log: Log, protected val dbInjection: DbInjection): PanelLC(), MvpView<P> {

    protected abstract val presenter: P

    override fun onBindView(context: Context, view: View) {
        super.onBindView(context, view)

        presenter.onViewAttach(onBindPresenterView())
    }

    override fun <T : Any> getFeature(type: KClass<T>): T? = mvpView.getFeature(type)

    override fun onException(exception: AppException) = mvpView.onException(exception)

    override fun initPresenter(presenter: P?) {
    }


    override fun onDestroyView() {
        presenter.onViewDetach()
    }

    abstract fun onBindPresenterView(): V
}