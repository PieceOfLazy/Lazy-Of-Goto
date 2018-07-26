package lazy.of.go.to.domain.entity

data class SettingEntity(
        val idx: String,
        val referencePath: String,
        val recordIdx: String,
        val name: String = "",
        val location: LocationEntity = LocationEntity(),
        val time: SettingTime = SettingTime(),
        val settingWeek: SettingWeek = SettingWeek()
)