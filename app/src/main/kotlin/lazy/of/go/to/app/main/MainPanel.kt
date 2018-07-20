package lazy.of.go.to.app.main

import android.content.Context
import lazy.of.framework.library.panel.PanelLC
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.framework.library.util.Log
import lazy.of.go.to.R
import lazy.of.go.to.domain.entity.SettingReference

/**
 * @author lazy.of.zpdl
 */
class MainPanel constructor(val settingReference: SettingReference, val listener: OnPanelListener? = null): PanelLC() {

    interface OnPanelListener {
    }

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {
        Log.d("KKH", "onBindLayoutContainer ")
    }

    override fun onDestroyView() {
        Log.d("KKH", "onDestroyView ")
    }

    override fun onResume() {
        Log.d("KKH", "onResume ")
    }

    override fun onPause() {
        Log.d("KKH", "onPause ")
    }

    override fun onLayout(): Int = R.layout.main_panel
}