package lazy.of.go.to.db.data

import com.google.firebase.firestore.GeoPoint
import lazy.of.go.to.domain.entity.SettingTime
import lazy.of.go.to.domain.entity.SettingWeek

data class SettingData(
        val idx: String,
        val referencePath: String,
        val recordIdx: String,
        val name: String = "",
        val location: GeoPoint? = null,
        val time: SettingTime = SettingTime(),
        val settingWeek: SettingWeek = SettingWeek()
)