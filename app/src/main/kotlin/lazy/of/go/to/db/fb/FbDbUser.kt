package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.domain.data.DbUser
import lazy.of.go.to.domain.entity.User
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

class FbDbUser constructor(private val db: FirebaseFirestore, private val listener: DbListener): DbUser {
    companion object {
        const val DB_NAME = "User"
    }

    private var _user: User? = null

    override fun set(user: User): Observable<Unit> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(this.listener.getUserUUID())
                    .set(user)
                    .addOnSuccessListener {
                        _user = user
                        emit.onNext(Unit)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        _user = null
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun get(): Observable<User> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(this.listener.getUserUUID())
                    .get()
                    .addOnSuccessListener {
                        it.toObject(User::class.java)?.let {
                            _user = it
                            emit.onNext(it)
                            emit.onComplete()
                        } ?: run {
                            _user = null
                            emit.onError(AppException(AppExceptionCode.USER_IS_NULL))
                        }
                    }
                    .addOnFailureListener {
                        _user = null
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun getUser(): User {
        return _user ?: User()
    }
}