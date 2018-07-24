package lazy.of.go.to.domain.entity

data class SettingReference(
        val idx: String = "",
        val userUUID: String = "",
        val settingIdx: String = "",
        val recordIdx: String = "",
        var updated: Boolean = false,
        var valid: Boolean = false
)