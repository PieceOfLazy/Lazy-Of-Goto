package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.db.data.UserDbData
import lazy.of.go.to.db.mapper.EntityMapper
import lazy.of.go.to.domain.data.UserRepository
import lazy.of.go.to.domain.entity.UserEntity
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

class UserDb constructor(private val db: FirebaseFirestore, private val listener: DbListener): UserRepository {
    companion object {
        const val DB_NAME = "User"
    }

    private var _user: UserDbData? = null

    private val entityMapper = object : EntityMapper<UserEntity, UserDbData> {
        override fun fromObject(obj: UserDbData): UserEntity {
            return UserEntity(
                    obj.isAnonymous,
                    obj.providerId,
                    obj.name,
                    obj.email,
                    obj.photoUrl
            )
        }

        override fun toObject(obj: UserEntity): UserDbData {
            return UserDbData(
                    obj.isAnonymous,
                    obj.providerId,
                    obj.name,
                    obj.email,
                    obj.photoUrl
            )
        }
    }

    override fun set(user: UserEntity): Observable<Unit> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(this.listener.getUserUUID())
                    .set(entityMapper.toObject(user))
                    .addOnSuccessListener {
                        _user = entityMapper.toObject(user)
                        emit.onNext(Unit)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        _user = null
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun get(): Observable<UserEntity> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(this.listener.getUserUUID())
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        documentSnapshot.toObject(UserDbData::class.java)?.let {
                            _user = it
                            emit.onNext(entityMapper.fromObject(it))
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

    override fun getUser(): UserEntity {
        return if(_user != null) {
            entityMapper.fromObject(_user!!)
        } else {
            UserEntity()
        }
    }
}