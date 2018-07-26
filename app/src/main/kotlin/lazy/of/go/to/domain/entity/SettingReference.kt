package lazy.of.go.to.domain.entity

import java.util.*

data class SettingReference(
        val idx: String = "",
        val userUUID: String = "",
        val settingIdx: String = "",
        val recordIdx: String = "",
        val settingUpdated: Date = Date(),
        val recordUpdated: Date = Date(),
        var valid: Boolean = true
)