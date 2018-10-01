package lazy.of.go.to.db.fb

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.db.data.SettingDbData
import lazy.of.go.to.db.data.SettingRefDbData
import lazy.of.go.to.db.mapper.EntityMapper
import lazy.of.go.to.domain.data.SettingRefRep
import lazy.of.go.to.domain.entity.SettingRefEntity
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode
import java.util.*


class SettingRefDb constructor(private val db: FirebaseFirestore, private val listener: DbListener): SettingRefRep {

    companion object {
        const val DB_NAME = "SettingReference"
    }

    private val entityMapper = object : EntityMapper<SettingRefEntity, SettingRefDbData> {
        override fun fromObject(obj: SettingRefDbData): SettingRefEntity {
            return SettingRefEntity(
                    obj.idx,
                    obj.userUUID,
                    obj.settingIdx,
                    obj.recordIdx,
                    obj.settingUpdated.toDate(),
                    obj.recordUpdated.toDate()
            )
        }

        override fun toObject(obj: SettingRefEntity): SettingRefDbData {
            return SettingRefDbData(
                    obj.idx,
                    obj.userUUID,
                    obj.settingIdx,
                    obj.recordIdx,
                    Timestamp(obj.settingUpdated),
                    Timestamp(obj.recordUpdated),
                    true
            )
        }
    }

    override fun create(userUUID: String): Observable<Unit> {
        return Observable.create { emit ->
            val document = db.collection(getCollectionPath(userUUID)).document()

            val batch = db.batch()
            val recordRef = RecordsDb.getDocument(db)
            val recordIdx = recordRef.id

            val settingRef = SettingDb.getDocument(db)
            val settingIdx = settingRef.id

            batch.set(
                    settingRef,
                    SettingDbData(
                            settingIdx,
                            document.path,
                            recordIdx)
            )

            batch.set(
                    document,
                    SettingRefDbData(
                            document.id,
                            userUUID,
                            settingIdx,
                            recordIdx,
                            Timestamp(Date()),
                            Timestamp(Date()),
                            true)
            )

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

    override fun set(settingReference: SettingRefEntity): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(): Observable<List<SettingRefEntity>> {
        return Observable.create { emit ->
            db.collection(getCollectionPath(listener.getUserUUID()))
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val results = mutableListOf<SettingRefEntity>()
                        querySnapshot.forEach {
                            val settingRefDbData = it.toObject(SettingRefDbData::class.java)
                            if(settingRefDbData.valid) {
                                results.add(entityMapper.fromObject(settingRefDbData))
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
        return "${UserDb.DB_NAME}/$userUUID/${SettingRefDb.DB_NAME}"
    }
}