package lazy.of.go.to.db.data

import com.google.firebase.firestore.GeoPoint

data class SettingDbData(
        val idx: String = "",
        val referencePath: String = "",
        val recordIdx: String = "",
        val name: String = "",
        val location: GeoPoint? = null,
        val monday: SettingTimeDbData = SettingTimeDbData(),
        val tuesday: SettingTimeDbData = SettingTimeDbData(),
        val wednesday: SettingTimeDbData = SettingTimeDbData(),
        val thursday: SettingTimeDbData = SettingTimeDbData(),
        val friday: SettingTimeDbData = SettingTimeDbData(),
        val saturday: SettingTimeDbData = SettingTimeDbData(),
        val sunday: SettingTimeDbData = SettingTimeDbData()
)