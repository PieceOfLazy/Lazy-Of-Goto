package lazy.of.go.to.domain.entity

import java.util.*

data class SettingRefEntity(
        val idx: String,
        val userUUID: String,
        val settingIdx: String,
        val recordIdx: String,
        val settingUpdated: Date,
        val recordUpdated: Date
)