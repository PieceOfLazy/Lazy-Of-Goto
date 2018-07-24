package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.entity.Setting
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode


class FbDbSettingReference constructor(private val db: FirebaseFirestore, private val listener: DbListener): DbSettingReference {

    companion object {
        const val DB_NAME = "SettingReference"
    }

    override fun add(settingReference: SettingReference): Observable<Unit> {
        return Observable.create { emit ->
            val documentSettingReference = db.collection(getCollectionPath(settingReference.userUUID)).document()

            val batch = db.batch()
            var recordIdx = settingReference.recordIdx

            if(StringUtil.isEmpty(recordIdx)) {
                val documentReference = FbDbRecords.getDocument(db)
//                    batch.set(documentReference, "")
                recordIdx = documentReference.id
            }

            var settingIdx = settingReference.settingIdx
            if (StringUtil.isEmpty(settingIdx)) {
                val documentReference = FbDbSetting.getDocument(db)
                settingIdx = documentReference.id
                batch.set(documentReference,
                        Setting(settingIdx,
                                documentSettingReference.path,
                                recordIdx)
                )
            }

            val referenceData = SettingReference(
                    documentSettingReference.id,
                    settingReference.userUUID,
                    settingIdx,
                    recordIdx
                    )

            batch.set(documentSettingReference, referenceData)
            batch
                    .commit()
                    .addOnSuccessListener {
                        emit.onNext(Unit)
                        emit.onComplete()
                    }.addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }


//        db.runTransaction { transaction ->
//            var recordIdx = settingReference.recordIdx
//
//            if(StringUtil.isEmpty(recordIdx)) {
//                val documentReference = FbDbRecords.getDocument(db)
////                    transaction.set(documentReference, "")
//                recordIdx = documentReference.id
//            }
//
//            var settingIdx = settingReference.settingIdx
//            if(StringUtil.isEmpty(settingIdx)) {
//                val documentReference = FbDbSetting.getDocument(db)
//                settingIdx = documentReference.id
//                transaction.set(documentReference,
//                        Setting(settingIdx,
//                                recordIdx)
//                )
//            }
//
////                val documentReference = db.collection(getCollectionPath(settingReference.userUUID)).document()
////                val documentSetting = FbDbSetting.getDocument(db, settingReference.settingIdx)
////                val documentRecords = FbDbRecords.getDocument(db, settingReference.recordIdx)
////
////                if(documentSetting == null) {
////
////                }
////                val snapshotSettingReference = transaction.get(documentReference)
////                if(snapshotSettingReference.exists()) {
////                    Log.d("KKH", "snapshotSettingReference.exists() : "+snapshotSettingReference.exists())
////                } else {
////
////                }
//
////                val snapshotRecords = transaction.get(documentRecords)
////
////                val reference = snapshotSettingReference.toObject(SettingReference::class.java)
////                val result: SettingReference = if (reference != null) {
////                    reference
////                } else {
////                    val tmp = SettingReference(
////                            documentReference.id,
////                            settingReference.userUUID,
////                            documentSetting.id,
////                            documentRecords.id,
////                            false,
////                            true)
//////                    transaction.set(documentReference, tmp)
////                    tmp
////                }
////
////                val setting = snapshotSetting.toObject(Setting::class.java)
////                if (setting == null) {
//////                    transaction.set(documentSetting, Setting(
//////                            documentSetting.id, documentRecords.id
//////                    ))
////                }
//
////                try {
////                    val snapshotSetting = transaction.get(documentSetting)
////                } catch (e: Exception) {
////
////                }
//            SettingReference(
//                    "",
//                    settingReference.userUUID,
//                    settingIdx,
//                    recordIdx,
//                    false,
//                    true)
//        }.addOnSuccessListener {
//            Log.d("KKH", "it : "+it.toString())
//            emit.onNext(Unit)
//            emit.onComplete()
//        }.addOnFailureListener {
//            emit.onError(AppException(AppExceptionCode.DB, it))
//        }
//    }
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

//    private fun getRecordIdx(idx: String): String {
//        return if (StringUtil.isEmpty(idx)) {
//            db.collection(FbDbRecords.DB_NAME).document().id
//        } else {
//            idx
//        }
//    }
}