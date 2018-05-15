package lazy.of.go.to.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.main_view.*
import lazy.of.framework.library.panel.PanelLC
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.go.to.R
import lazy.of.go.to.common.Log
import javax.inject.Inject

/**
 * Created by piece.of.lazy
 */
class MainView @Inject constructor(log: Log): PanelLC(), MainContract.View {


    init {
        log.d("Create MainView")
    }

    protected var _presenter: MainContract.Presenter? = null

    override fun setPresenter(presenter: MainContract.Presenter?) {
        _presenter = presenter
    }

    override fun onLayout(): Int = R.layout.main_view

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {

    }

    override fun onLanuch() {
        layoutContainer?.run {
            main_view_content
                    .animate()
                    .withLayer()
                    .alpha(1.0f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(1000)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(p0: Animator?) {

                        }
                    })
                    .start()
        }
    }
}