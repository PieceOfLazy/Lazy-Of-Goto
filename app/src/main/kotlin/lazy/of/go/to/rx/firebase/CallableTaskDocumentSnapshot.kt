package lazy.of.go.to.rx.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Observable
import lazy.of.framework.library.util.Log
import java.util.concurrent.Callable

class CallableTaskDocumentSnapshot constructor(private val task: Task<DocumentSnapshot>, private val timeout: Long = 0L): Callable<Observable<DocumentSnapshot>> {

    private val lock = java.lang.Object()

    private var snapshot: DocumentSnapshot? = null
    private var exception: Exception? = null

    override fun call(): Observable<DocumentSnapshot> {
        task
                .addOnSuccessListener {
                    Log.i("CallableTask", "addOnSuccessListener")
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }
                .addOnFailureListener {
                    Log.i("CallableTask", "addOnSuccessListener")
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }
                .addOnCompleteListener {
                    Log.i("CallableTask", "addOnSuccessListener")
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }
                .addOnCanceledListener {
                    Log.i("CallableTask", "addOnSuccessListener")
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }

        synchronized(lock) {
            if(snapshot == null && exception == null) {
                if(timeout > 0) {
                    lock.wait(timeout)
                } else {
                    lock.wait()
                }
            }
        }

        return when {
            snapshot != null -> Observable.just(snapshot)
            exception != null -> Observable.error(exception)
            else -> Observable.error(Exception("Time out"))
        }
    }
}