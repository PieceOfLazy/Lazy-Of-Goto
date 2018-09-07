package lazy.of.go.to.domain.entity

data class SettingTimeEntity(
        val workingDay: Boolean,
        val goTime: Int?,
        val backTime: Int?
)