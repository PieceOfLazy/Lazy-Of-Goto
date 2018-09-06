package lazy.of.go.to.db.fb

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.common.DateUtil
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.db.data.RecordDbData
import lazy.of.go.to.db.mapper.EntityMapper
import lazy.of.go.to.db.mapper.LocationEntityMapper
import lazy.of.go.to.db.mapper.RecordTimeEntityMapper
import lazy.of.go.to.domain.data.RecordRepository
import lazy.of.go.to.domain.entity.EntitySet
import lazy.of.go.to.domain.entity.RecordEntity
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode
import java.util.*


class RecordsDb constructor(private val db: FirebaseFirestore) : RecordRepository {

    companion object {
        private const val DB_NAME = "Records"
        private const val COLLECTION_NAME = "Collection"

        fun getIdx(db: FirebaseFirestore, idx: String): String {
            return if (StringUtil.isEmpty(idx)) {
                db.collection(FbDbSetting.DB_NAME).document().id
            } else {
                idx
            }
        }

        fun getDocument(db: FirebaseFirestore, idx: String = ""): DocumentReference {
            return if(StringUtil.isEmpty(idx)) {
                db.collection(DB_NAME).document()
            } else {
                db.collection(DB_NAME).document(idx)
            }
        }
    }

    private val entityMapper = object : EntityMapper<RecordEntity, RecordDbData> {
        override fun fromObject(obj: RecordDbData): RecordEntity {
            return RecordEntity(
                    obj.date.toDate(),
                    obj.name,
                    LocationEntityMapper.fromNullableObject(obj.location),
                    obj.settingTime,
                    RecordTimeEntityMapper.fromNullableObject(obj.recordTime)
            )
        }

        override fun toObject(obj: RecordEntity): RecordDbData {
            return RecordDbData(
                    Timestamp(obj.date),
                    obj.name,
                    LocationEntityMapper.toNullableObject(obj.location),
                    obj.settingTime,
                    RecordTimeEntityMapper.toNullableObject(obj.recordTime)
            )
        }
    }

    override fun set(idx: String, entity: RecordEntity): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(idx: String, date: Date): Observable<EntitySet<RecordEntity>> {
        val timeStamp = Timestamp(DateUtil.basedDayDate(date))
        return Observable.create { emit ->
            getCollectionReference(db, idx)
                    .whereEqualTo("date", timeStamp)
                    .get()
                    .addOnSuccessListener { querySnapShot ->
                        var result: RecordEntity? = null
                        querySnapShot.forEach { queryDocumentSnapShot ->
                            queryDocumentSnapShot.toObject(RecordDbData::class.java).let {
                                result = entityMapper.fromObject(it)
                            }
                        }
                        emit.onNext(EntitySet(result))
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun get(idx: String, startDate: Date, endDate: Date): Observable<List<RecordEntity>> {
        val startTimeStamp = Timestamp(startDate)
        val endTimeStamp = Timestamp(endDate)
        return Observable.create { emit ->
            getCollectionReference(db, idx)
                    .whereGreaterThanOrEqualTo("date", startTimeStamp)
                    .whereLessThanOrEqualTo("date", endTimeStamp)
                    .get()
                    .addOnSuccessListener { querySnapShot ->
                        val results = mutableListOf<RecordEntity>()
                        querySnapShot.forEach { queryDocumentSnapShot ->
                            queryDocumentSnapShot.toObject(RecordDbData::class.java).let {
                                results.add(entityMapper.fromObject(it))
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

    private fun getCollectionReference(db: FirebaseFirestore, idx: String): CollectionReference {
        return RecordsDb.getDocument(db, idx).collection(RecordsDb.COLLECTION_NAME)
    }
}