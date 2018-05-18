package lazy.of.go.to.widget.panel

import android.content.Context
import android.view.View
import lazy.of.framework.library.panel.PanelLC
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.go.to.R

class LoadingPanel: PanelLC() {
    private var count = 0

    override fun onLayout(): Int = R.layout.panel_loading

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {

    }

    fun loadingStart() {
        if(count <= 0) {
            count = 1
            layoutContainer?.containerView?.visibility = View.VISIBLE
        } else {
            count++
        }
    }

    fun loadingEnd() {
        if(count <= 1) {
            count = 0
            layoutContainer?.containerView?.visibility = View.GONE
        } else {
            count--
        }
    }
}