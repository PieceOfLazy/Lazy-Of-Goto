package lazy.of.go.to.db.mapper

import lazy.of.go.to.db.data.SettingTimeDbData
import lazy.of.go.to.domain.entity.SettingTimeEntity

class SettingTimeMapper {

    companion object : EntityNullableMapper<SettingTimeEntity, SettingTimeDbData>() {
        override fun fromObject(obj: SettingTimeDbData): SettingTimeEntity {
            return SettingTimeEntity(
                    obj.workingDay,
                    obj.goTime,
                    obj.backTime)
        }

        override fun toObject(obj: SettingTimeEntity): SettingTimeDbData {
            return SettingTimeDbData(
                    obj.workingDay,
                    obj.goTime,
                    obj.backTime)
        }
    }
}