package lazy.of.go.to.db.firebase

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.framework.library.util.Log
import lazy.of.go.to.db.DbUser
import lazy.of.go.to.db.OnDbListener
import lazy.of.go.to.db.data.User

class FbDbUser constructor(private val db: FirebaseFirestore, private val listener: OnListener): DbUser {

    interface OnListener {
        fun getUserUUID(): String
    }

    private var _user: User? = null

    override fun observableSetUser(user: User): Observable<Unit> {
        return Observable.create({ emit ->
            db.collection("User").document(this.listener.getUserUUID())
                    .set(user)
                    .addOnSuccessListener {
                        _user = user
                        emit.onNext(Unit)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        _user = null
                        emit.onError(it)
                    }
        })
    }

    override fun observableGetUser(user: User): Observable<User> {
        return observableSetUser(user)
                .flatMap {
                    observableGetUser()
                }
    }

    override fun observableGetUser(): Observable<User> {
        return Observable.create({ emit ->
            db.collection("User").document(this.listener.getUserUUID())
                    .get()
                    .addOnSuccessListener {
                        it.toObject(User::class.java)?.let {
                            _user = it
                            emit.onNext(it)
                            emit.onComplete()
                        } ?: run {
                            _user = null
                            emit.onError(Exception("User is null"))
                        }
                    }
                    .addOnFailureListener {
                        _user = null
                        emit.onError(it)
                    }
        })
    }

    override fun getUser(): User {
        return _user ?: User()
    }
}