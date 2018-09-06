package lazy.of.go.to.domain.entity

data class SettingTimeEntity(
        val holiday: Boolean = false,
        val goTime: Int? = null,
        val backTime: Int? = null
)