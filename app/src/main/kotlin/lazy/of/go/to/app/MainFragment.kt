package lazy.of.go.to.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.main_view.*
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpFragment
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.di.FragmentScoped
import javax.inject.Inject

/**
 * Created by piece.of.lazy
 */
@ActivityScoped
class MainFragment @Inject constructor(): MvpFragment<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun onBindPresenterView(): MainContract.View = this

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            inflater?.let {
                return it.inflate(R.layout.main_view, container, false)
            }
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        getPresenter()?.onLaunch()
    }

    override fun onLaunch() {
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