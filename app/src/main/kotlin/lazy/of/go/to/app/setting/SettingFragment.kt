package lazy.of.go.to.app.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_setting_fragment.*
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpFragment
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SettingFragment @Inject constructor(): MvpFragment<SettingContract.View, SettingContract.Presenter>(), SettingContract.View {

    interface OnFragmentListener {
        fun onLogout()
    }
    private var listener: OnFragmentListener? = null

    override fun onBindPresenterView(): SettingContract.View = this

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnFragmentListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            inflater?.let {
                return it.inflate(R.layout.activity_setting_fragment, container, false)
            }
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity_setting_fragment_logout.setOnClickListener({
            _presenter?.logout()
        })
        activity_setting_fragment_crash.setOnClickListener({
            throw RuntimeException("This is a crash")
        })
    }

    override fun onResume() {
        super.onResume()

        _presenter?.onLaunch()
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    override fun onLogout() {
        listener?.onLogout()
    }
}