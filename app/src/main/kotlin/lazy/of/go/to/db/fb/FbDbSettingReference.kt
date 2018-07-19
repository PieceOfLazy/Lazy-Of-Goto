package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode


class FbDbSettingReference constructor(private val db: FirebaseFirestore, private val listener: DbListener): DbSettingReference {

    companion object {
        const val DB_NAME = "SettingReference"
    }

    override fun add(settingReference: SettingReference): Observable<Unit> {
        val addDocument = db.collection(getCollectionPath(settingReference.userUUID)).document()
        val addSettingReference = SettingReference(
                addDocument.id,
                settingReference.userUUID,
                getSettingPath(settingReference.settingPath),
                getRecordPath(settingReference.recordPath),
                false,
                true
                )

        return Observable.create { emit ->
            addDocument.set(addSettingReference)
                    .addOnSuccessListener {
                        emit.onNext(Unit)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun set(settingReference: SettingReference): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(): Observable<List<SettingReference>> {
        return Observable.create { emit ->
            db.collection(getCollectionPath(listener.getUserUUID()))
                    .get()
                    .addOnSuccessListener {
                        val results = mutableListOf<SettingReference>()
                        it.forEach {
                            it.toObject(SettingReference::class.java).let {
                                if(it.valid) {
                                    results.add(it)
                                }
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

    private fun getCollectionPath(userUUID: String): String {
        return "${FbDbUser.DB_NAME}/$userUUID/${FbDbSettingReference.DB_NAME}"
    }

    private fun getSettingPath(path: String): String {
        return if (StringUtil.isEmpty(path)) {
            "${FbDbSetting.DB_NAME}/${db.collection(FbDbSetting.DB_NAME).document().id}"
        } else {
            path
        }
    }

    private fun getRecordPath(recordPath: String): String {
        return if (StringUtil.isEmpty(recordPath)) {
            "${FbDbRecords.DB_NAME}/${db.collection(FbDbRecords.DB_NAME).document().id}"
        } else {
            recordPath
        }
    }
}