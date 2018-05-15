package lazy.of.go.to.base

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import lazy.of.framework.library.util.Log
import lazy.of.go.to.common.LogImpl

/**
 * @author lazy.of.zpdl
 */
abstract class BaseActivity: DaggerAppCompatActivity(), BaseListener {

    val log by lazy {
        LogImpl(this::class.simpleName)
    }

    override fun onLoadingStart() {
//        if(loadingPiece == null) {
//            loadingPiece = LoadingPiece().apply {
//                doCreateView(this@BaseActivity, findViewById<View>(android.R.id.content).rootView as? ViewGroup)
//            }
//        }
//
//        loadingPiece?.start()
    }

    override fun onLoadingEnd() {
//        loadingPiece?.end(100)
    }

    override fun onShowToast(msg: String, duration: Int) {
        Handler(Looper.getMainLooper()).post({
            Toast.makeText(this, msg, duration).show()
        })
    }

    override fun onShowToast(msg: Int, duration: Int) {
        Handler(Looper.getMainLooper()).post({
            Toast.makeText(this, msg, duration).show()
        })
    }
}
//abstract class BaseActivity<C: Contract<*,*>> : DaggerAppCompatActivity(), BaseListener {
//
//    @Inject
//    lateinit var contract: C
//
//    protected val log by lazy {
//        Log(this::class.simpleName)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        onSetContentView(savedInstanceState)
//
//        contract.attach(this, activity_base_frame)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        contract.detach()
//    }
//
//    open fun onSetContentView(savedInstanceState: Bundle?) {
//        setContentView(R.layout.activity_base)
//    }
//
//    override fun onLoadingStart() {
////        if(loadingPiece == null) {
////            loadingPiece = LoadingPiece().apply {
////                doCreateView(this@BaseActivity, findViewById<View>(android.R.id.content).rootView as? ViewGroup)
////            }
////        }
////
////        loadingPiece?.start()
//    }
//
//    override fun onLoadingEnd() {
////        loadingPiece?.end(100)
//    }
//
//    override fun onShowToast(msg: String, duration: Int) {
//        Handler(Looper.getMainLooper()).post({
//            Toast.makeText(this, msg, duration).show()
//        })
//    }
//
//    override fun onShowToast(msg: Int, duration: Int) {
//        Handler(Looper.getMainLooper()).post({
//            Toast.makeText(this, msg, duration).show()
//        })
//    }
//}