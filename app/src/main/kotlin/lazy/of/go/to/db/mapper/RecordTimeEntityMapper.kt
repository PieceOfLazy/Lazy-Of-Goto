package lazy.of.go.to.db.mapper

import lazy.of.go.to.db.data.RecordTimeDbData
import lazy.of.go.to.domain.entity.RecordTimeEntity

class RecordTimeEntityMapper {

    companion object : EntityNullableMapper<RecordTimeEntity, RecordTimeDbData>() {
        override fun fromObject(obj: RecordTimeDbData): RecordTimeEntity {
            return RecordTimeEntity(
                    obj.goCheckTime,
                    obj.goRecordTime,
                    obj.goPhotoUrl,
                    obj.backCheckTime,
                    obj.backRecordTime,
                    obj.backPhotoUrl)
        }

        override fun toObject(obj: RecordTimeEntity): RecordTimeDbData {
            return RecordTimeDbData(
                    obj.goCheckTime,
                    obj.goRecordTime,
                    obj.goPhotoUrl,
                    obj.backCheckTime,
                    obj.backRecordTime,
                    obj.backPhotoUrl)
        }
    }
}