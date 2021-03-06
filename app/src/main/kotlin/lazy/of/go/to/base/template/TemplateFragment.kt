package lazy.of.go.to.base.template

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpFragment
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class TemplateFragment @Inject constructor(): MvpFragment<TemplateContract.View, TemplateContract.Presenter>(), TemplateContract.View {

    interface OnFragmentListener {
    }
    private var listener: OnFragmentListener? = null

    override fun onBindPresenterView(): TemplateContract.View = this

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is OnFragmentListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            inflater?.let {
                return it.inflate(R.layout.activity_base, container, false)
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
}