package lazy.of.go.to.domain.entity

import lazy.of.go.to.type.WeekType

data class SettingEntity(
        val idx: String,
        val referencePath: String,
        val recordIdx: String,
        val name: String,
        val location: LocationEntity,
        val monday: SettingTimeEntity,
        val tuesday: SettingTimeEntity,
        val wednesday: SettingTimeEntity,
        val thursday: SettingTimeEntity,
        val friday: SettingTimeEntity,
        val saturday: SettingTimeEntity,
        val sunday: SettingTimeEntity
) {
    fun getSettingTime(weekType: WeekType): SettingTimeEntity {
        return when(weekType) {
            WeekType.MONDAY -> monday
            WeekType.TUESDAY -> tuesday
            WeekType.WEDNESDAY -> wednesday
            WeekType.THURSDAY -> thursday
            WeekType.FRIDAY -> friday
            WeekType.SATURDAY -> saturday
            WeekType.SUNDAY -> sunday
        }
    }
}