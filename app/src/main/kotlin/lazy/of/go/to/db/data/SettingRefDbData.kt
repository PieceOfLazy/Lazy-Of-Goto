package lazy.of.go.to.db.data

import com.google.firebase.Timestamp
import java.util.*

data class SettingRefDbData(
        val idx: String = "",
        val userUUID: String = "",
        val settingIdx: String = "",
        val recordIdx: String = "",
        val settingUpdated: Timestamp = Timestamp(Date()),
        val recordUpdated: Timestamp = Timestamp(Date()),
        var valid: Boolean = false
)