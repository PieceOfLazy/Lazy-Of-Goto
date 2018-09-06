package lazy.of.go.to.db.fb

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import io.reactivex.Observable
import lazy.of.go.to.common.StringUtil
import lazy.of.go.to.db.data.SettingData
import lazy.of.go.to.db.mapper.EntityMapper
import lazy.of.go.to.domain.data.DbSetting
import lazy.of.go.to.domain.entity.LocationEntity
import lazy.of.go.to.domain.entity.SettingEntity
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

    private val entityMapper = object : EntityMapper<SettingEntity, SettingData> {
        override fun fromObject(obj: SettingData): SettingEntity {
            return SettingEntity(
                    obj.idx,
                    obj.referencePath,
                    obj.recordIdx,
                    obj.name,
                    if(obj.location != null) LocationEntity(obj.location.latitude, obj.location.longitude) else LocationEntity(),
                    obj.time,
                    obj.settingWeek
            )
        }

        override fun toObject(obj: SettingEntity): SettingData {
            return SettingData(
                    obj.idx,
                    obj.referencePath,
                    obj.recordIdx,
                    obj.name,
                    if(obj.location.isValid()) GeoPoint(obj.location.latitude, obj.location.longitude) else null,
                    obj.time,
                    obj.settingWeek
            )
        }
    }

    override fun set(setting: SettingEntity): Observable<Unit> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(setting.idx)
                    .set(entityMapper.toObject(setting))
                    .addOnSuccessListener {
                        emit.onNext(Unit)
                        emit.onComplete()
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }

    override fun get(idx: String): Observable<SettingEntity> {
        return Observable.create { emit ->
            db.collection(DB_NAME).document(idx)
                    .get()
                    .addOnSuccessListener {
                        it.toObject(SettingData::class.java)?.let {
                            emit.onNext(entityMapper.fromObject(it))
                            emit.onComplete()
                        } ?: run {
                            emit.onError(AppException(AppExceptionCode.DB_EMPTY))
                        }
                    }
                    .addOnFailureListener {
                        emit.onError(AppException(AppExceptionCode.DB, it))
                    }
        }
    }
}