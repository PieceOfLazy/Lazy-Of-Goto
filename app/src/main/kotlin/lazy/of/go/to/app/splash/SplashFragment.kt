package lazy.of.go.to.app.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.splash_view.*
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpFragment
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SplashFragment @Inject constructor(): MvpFragment<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    interface OnFragmentListener {
        fun onLogin()
        fun onMain()
    }
    private var listener: OnFragmentListener? = null

    override fun onBindPresenterView(): SplashContract.View = this

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnFragmentListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            inflater?.let {
                return it.inflate(R.layout.splash_view, container, false)
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

        presenter?.onLaunch()
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    override fun onLaunch() {
        splash_view_content
                .animate()
                .withLayer()
                .alpha(1.0f)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(1000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(p0: Animator?) {
                        presenter?.onAnimationEnd()
                    }
                })
                .start()
    }

    override fun onFinish(isLogin: Boolean) {
        if(isLogin) {
            listener?.onMain()
        } else {
            listener?.onLogin()
        }
    }
}