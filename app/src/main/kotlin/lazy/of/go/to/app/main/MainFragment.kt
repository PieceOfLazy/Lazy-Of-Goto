package lazy.of.go.to.app.main

import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_fragment.*
import lazy.of.framework.library.panel.PanelBase
import lazy.of.framework.library.panel.viewpager.ViewPagerPanelAdapter
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpFragment
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.domain.entity.SettingReference
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class MainFragment @Inject constructor(): MvpFragment<MainContract.View, MainContract.Presenter>(), MainContract.View {

    interface OnFragmentListener {
        fun onLogin()
        fun onMain()
    }
    private var listener: OnFragmentListener? = null

    @Inject
    lateinit var dbInjection: DbInjection

    private var adapter: Adapter? = null

    override fun onBindPresenterView(): MainContract.View = this

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnFragmentListener) {
            listener = context
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            inflater?.let {
                return it.inflate(R.layout.main_fragment, container, false)
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
        adapter?.destroy()
    }

    override fun onLaunch(list: List<SettingReference>) {
        val panels = mutableListOf<PanelBase>()
        for(settingReference in list) {
            panels.add(MainPanel(this, log, dbInjection, settingReference))
        }
//        panels.add(MainPanel(this, log, dbInjection, SettingReference()))
//        panels.add(MainPanel(this, log, dbInjection, SettingReference()))

        adapter = Adapter(context, panels)

        main_fragment_view_pager.adapter = adapter
        main_fragment_view_pager.offscreenPageLimit = 1
        main_fragment_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                adapter?.getBindItem(position)?.onResume()
            }
        })
        adapter?.getBindItem(main_fragment_view_pager.currentItem)?.onResume()
    }

    private class Adapter(context: Context, val list: List<PanelBase>) : ViewPagerPanelAdapter(context) {
        override fun getBindItem(position: Int): PanelBase? {
            return if (position in 0 until list.size)
                list[position]
            else
                null
        }

        override fun getCount(): Int = list.size

        override fun getBindItemPosition(panel: PanelBase): Int {
            return list.indexOf(panel)
        }

        override fun destroy() {
            for(panel in list) {
                panel.onDestroyView()
            }
        }
    }
}