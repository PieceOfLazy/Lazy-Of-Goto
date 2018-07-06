package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

class FbDbSettingReference constructor(private val db: FirebaseFirestore, private val listener: DbListener): DbSettingReference {

    companion object {
        const val DB_NAME = "SettingReference"
    }

    override fun add(userUUID: String): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(settingReference: SettingReference): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(): Observable<List<SettingReference>> {
        return Observable.create { emit ->
            db.collection(getCollectionPath())
                    .get()
                    .addOnSuccessListener {
                        val results = mutableListOf<SettingReference>()
                        it.forEach {
                            it.toObject(SettingReference::class.java).let {
                                results.add(it)
                            }
                        }
                        emit.onNext(results)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }


//
//    override fun count() {
//        db.collection(getCollectionPath()).get().t
//    }

    private fun getCollectionPath(): String {
        return "${FbDbUser.DB_NAME}/${listener.getUserUUID()}/${FbDbSettingReference.DB_NAME}"
    }
}