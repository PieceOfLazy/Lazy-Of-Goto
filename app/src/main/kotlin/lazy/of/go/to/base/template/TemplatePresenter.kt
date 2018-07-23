package lazy.of.go.to.base.template

import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class TemplatePresenter @Inject constructor(private val log: Log): TemplateContract.Presenter {

    private var view: TemplateContract.View? = null
    private var launch = false

    override fun onViewAttach(view: TemplateContract.View) {
        this.view = view
        this.view?.initPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
        }
    }
}