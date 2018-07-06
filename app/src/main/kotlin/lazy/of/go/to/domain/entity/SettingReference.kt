package lazy.of.go.to.domain.entity

data class SettingReference(
        val idx: String = "",
        val userUUID: String = "",
        val settingPath: String = "",
        val recordPath: String = "",
        var updated: Boolean = false,
        var valid: Boolean = false
)