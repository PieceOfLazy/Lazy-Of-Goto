package lazy.of.go.to.domain.entity

import com.google.firebase.Timestamp
import java.util.*

data class SettingReference(
        val idx: String = "",
        val userUUID: String = "",
        val settingIdx: String = "",
        val recordIdx: String = "",
        val settingUpdated: Timestamp = Timestamp(Date()),
        val recordUpdated: Timestamp = Timestamp(Date()),
        var valid: Boolean = true
)