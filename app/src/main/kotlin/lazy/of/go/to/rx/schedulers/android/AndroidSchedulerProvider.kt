package lazy.of.go.to.rx.schedulers.android

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lazy.of.go.to.rx.schedulers.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidSchedulerProvider @Inject constructor(): SchedulerProvider {

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

}