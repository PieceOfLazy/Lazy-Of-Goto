package lazy.of.go.to.db.fb

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.db.data.SettingReferenceData
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.db.mapper.EntityMapper
import lazy.of.go.to.domain.entity.SettingEntity
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode


class FbDbSettingReference constructor(private val db: FirebaseFirestore, private val listener: DbListener): DbSettingReference {

    companion object {
        const val DB_NAME = "SettingReference"
    }

    private val entityMapper = object : EntityMapper<SettingReference, SettingReferenceData> {
        override fun fromObject(obj: SettingReferenceData): SettingReference {
            return SettingReference(
                    obj.idx,
                    obj.userUUID,
                    obj.settingIdx,
                    obj.recordIdx,
                    obj.settingUpdated.toDate(),
                    obj.recordUpdated.toDate(),
                    obj.valid
            )
        }

        override fun toObject(obj: SettingReference): SettingReferenceData {
            return SettingReferenceData(
                    obj.idx,
                    obj.userUUID,
                    obj.settingIdx,
                    obj.recordIdx,
                    Timestamp(obj.settingUpdated),
                    Timestamp(obj.recordUpdated),
                    obj.valid
            )
        }
    }

    override fun add(settingReference: SettingReference): Observable<Unit> {
        return Observable.create { emit ->
            val documentSettingReference = db.collection(getCollectionPath(settingReference.userUUID)).document()

            val batch = db.batch()
            var recordIdx = settingReference.recordIdx

            if(StringUtil.isEmpty(recordIdx)) {
                val documentReference = RecordsDb.getDocument(db)
//                    batch.set(documentReference, "")
                recordIdx = documentReference.id
            }

            var settingIdx = settingReference.settingIdx
            if (StringUtil.isEmpty(settingIdx)) {
                val documentReference = FbDbSetting.getDocument(db)
                settingIdx = documentReference.id
                batch.set(documentReference,
                        SettingEntity(settingIdx,
                                documentSettingReference.path,
                                recordIdx)
                )
            }

            val settingReferenceData = SettingReferenceData(
                    documentSettingReference.id,
                    settingReference.userUUID,
                    settingIdx,
                    recordIdx
                    )

            batch.set(documentSettingReference, settingReferenceData)
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