package lazy.of.go.to.db

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.domain.entity.User

class DbRecord constructor(private val db: FirebaseFirestore, private val listener: DbListener) {

    private var _user: User? = null

    fun observableSetUser(user: User): Observable<Unit> {
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

    fun observableGetUser(): Observable<User> {
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

    fun getUser(): User {
        return _user ?: User()
    }
}