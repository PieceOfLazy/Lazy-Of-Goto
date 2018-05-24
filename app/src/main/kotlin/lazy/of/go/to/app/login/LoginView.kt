package lazy.of.go.to.app.login

import android.content.Context
import kotlinx.android.synthetic.main.login_view.*
import lazy.of.framework.library.panel.PanelLC
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.go.to.R
import lazy.of.go.to.base.feature.FeatureListener
import lazy.of.go.to.common.Log

class LoginView constructor(private val log: Log, private val listener: OnPanelListener) : PanelLC() {

    interface OnPanelListener: FeatureListener {
        fun onGoogleLogin()
        fun onAnonymouslyLogin()
    }

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {
        layoutContainer.login_view_google.setOnClickListener({
            listener.onGoogleLogin()
        })
        layoutContainer.login_view_anonymous.setOnClickListener({
            listener.onAnonymouslyLogin()
        })
    }

    override fun onLayout(): Int = R.layout.login_view
}