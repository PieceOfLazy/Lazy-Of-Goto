package lazy.of.go.to.db.fb

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.domain.data.DbSetting
import lazy.of.go.to.domain.entity.Setting
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode


class FbDbSetting constructor(private val db: FirebaseFirestore): DbSetting {
    companion object {
        const val DB_NAME = "Setting"

        fun getIdx(db: FirebaseFirestore, idx: String): String {
            return if (StringUtil.isEmpty(idx)) {
                db.collection(DB_NAME).document().id
            } else {
                idx
            }
        }

        fun getPath(idx: String): String {
            return "$DB_NAME/$idx"
        }

        fun getDocument(db: FirebaseFirestore, idx: String = ""): DocumentReference {
            return if(StringUtil.isEmpty(idx)) {
                db.collection(DB_NAME).document()
            } else {
                db.collection(DB_NAME).document(idx)
            }
        }
    }

    override fun set(setting: Setting): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(idx: String): Observable<Setting> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(idx)
                    .get()
                    .addOnSuccessListener {
                        it.toObject(Setting::class.java)?.let {
                            emit.onNext(it)
                            emit.onComplete()
                        } ?: run {
                            set(Setting(idx, "767"))
                        }
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }



}