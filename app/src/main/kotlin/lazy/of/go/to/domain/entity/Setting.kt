package lazy.of.go.to.domain.entity

import com.google.firebase.firestore.GeoPoint

data class Setting(
        val idx: String,
        val referencePath: String,
        val recordIdx: String,
        val name: String = "",
        val location: GeoPoint? = null,
        val time: SettingTime? = null,
        val settingWeek: SettingWeek = SettingWeek()
)